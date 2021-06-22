# Redis
Remote Dictionary Service
# 安装
```
下载redis-5.0.5.tar.gz
tar -zxvf redis-5.0.5.tar.gz
cd redis-5.0.5
// 安装
make
// 启动 server
src/redis-server
// 指定配置文件启动 server
src/redis-server conf/my.conf
// 后台启动
修改配置文件 daemonize yes
// 若要允许外网访问的话 注释掉 bind 127.0.0.1 就行了
# bind 127.0.0.1
// 测试
src/redis-cli
set fool aaa
get fool
del fool
```
    

# 搭建redis集群 3主3从
## 单机搭建集群，索引要用不同的端口
# 配置文件 其它服务需将7001改成其它数字
```
# KIyA
cluster-enabled yes
cluster-config-file 3m-3s-7001.conf
cluster-node-timeout 15000
requirepass liuli
masterauth liuli
daemonize yes
logfile "/usr/dev/redis-6.0.10/log/3m-3s/7001.log"
pidfile /usr/dev/redis-6.0.10/pid/3m-3s/7001.pid
dbfilename 7001.rdb
port 7001
# 需要被其它主机访问的话 注释掉bind 127.0.0.1
# 改为bind 192.168.137.2 其它主机可以访问到的一个ip 可以bind多个 如 bind 192.168.137.2 192.168.137.3
# bind 127.0.0.1
bind 192.168.137.2
```
# 启动服务
```
src/redis-server conf/3m-3s/7001.conf
src/redis-server conf/3m-3s/7002.conf
src/redis-server conf/3m-3s/7003.conf
src/redis-server conf/3m-3s/7004.conf
src/redis-server conf/3m-3s/7005.conf
src/redis-server conf/3m-3s/7006.conf
```
# 创建集群
```
--cluster-replicas 1 表示一个主节点需要多少从节点，3主3从所以是1，不加便是6主了
src/redis-cli --cluster create 192.168.137.2:7001 192.168.137.2:7002 192.168.137.2:7003 192.168.137.2:7004 192.168.137.2:7005 192.168.137.2:7006 --cluster-replicas 1 -a liuli
```
# 测试
```
src/redis-cli -h 192.168.137.2 -p 7001
auth liuli
192.168.137.2:7001> set bb cc
(error) MOVED 8620 192.168.137.2:7002
error MOVED ??? 节点会计算这个东西给谁处理，不在该槽，就会发生这个错误
这是因为启动redis-cli时没有设置集群模式所导致
使用 -c 来启动集群模式
src/redis-cli -h 192.168.137.2 -p 7001 -c
auth liuli
查看集群信息
cluster info
cluster nodes
cluster slots
```

# 集群扩容
```
新增两个配置文件并启动
添加主节点到集群 第一个ip:port是要添加到集群的节点，第二个ip:port是当前集群的任意节点
./src/redis-cli --cluster add-node 192.168.137.2:7007 192.168.137.2:7007 -a liuli
给新添加的节点分配哈希槽 选择分配的数量，接收节点，哈希槽来源
./src/redis-cli --cluster reshard  192.168.137.2:7007 -a liuli
# 添加从节点到集群 并指定主节点的节点id 第一个ip:port是要添加到集群的节点，第二个ip:port是当前集群的任意节点
./src/redis-cli --cluster add-node --cluster-slave --cluster-master-id db10a9d5c1662d9e3cee21c5776f2e9709f76619 192.168.137.2:7008 192.168.137.2:7001
```

# 集群缩容
```
先删除从节点 ip:port是当前集群的任意节点 后面是要删除的节点id
src/redis-cli --cluster del-node 192.168.137.2:7001 e996b5222eca3bf9dbaec3eaec38fb44761e3a2c -a liuli
删除主节点之前要将主节点的哈希槽分出去 选择的时候指定source为要删除的节点id
src/redis-cli --cluster reshard  192.168.137.2:7001 -a liuli
删除主节点
src/redis-cli --cluster del-node 192.168.137.2:7001 524d6232f8c62d41eaa9f79d5c33c40284d60b6b -a liuli
```

# redis集群启动脚本
```
#!/bin/bash

redis_path="/usr/dev/redis-6.0.10/"
redis_server="${redis_path}src/redis-server"
redis_conf="${redis_path}conf/3m-3s/700"

echo 'start'

for((i=1;i<7;i++))
do $redis_server "${redis_conf}${i}.conf"
done

echo 'end'
```

# 数据淘汰策略
## 当实际内存超出maxmemory时候用到淘汰策略
## 淘汰策略配置使用配置文件里的maxmemory-policy项
| 策略 | 描述 |
| --- | --- |
| noeviction | Don't evict anything, just return an error on write operations. DEFAULT |
| volatile-lru | Evict using approximated LRU, only keys with an expire set. |
| allkeys-lru | Evict any key using approximated LRU. |
| volatile-lfu | Evict using approximated LFU, only keys with an expire set. |
| allkeys-lfu | Evict any key using approximated LFU. |
| volatile-random | Remove a random key having an expire set. |
| allkeys-random | Remove a random key, any key. |
| volatile-ttl | Remove the key with the nearest expire time (minor TTL) |

# Redis内部数据结构
## RedisObject
```cpp
struct RedisObject{
    // 类型 4bits
    int4 type;
    // 存储格式 4bits
    int4 encoding;
    // lru信息 24bits
    int24 lru;
    // 引用计数 32bits
    int32 refcount;
    // 对象内容指针 64位系统 64bits 32位系统 32bits
    void *ptr;
}
```
## string SDS Simple Dynamic String
```cpp
// 为什么使用T不直接用int呢
// 因为字符串比较短时，可以用byte或short来表示，节约空间
struct SDS<T>{
    // 数组容量
    T alloc;
    // 数组长度
    T len;
    // 特殊标识位 低3位表示header类型
    unsigned char flags;
    // 数组内容
    char buf[];
}
```
## RedisDb
```cpp
struct RedisDb{
    // all keys key -> value
    dict *dict;
    // all expired keys key -> long(timestamp)
    dict *expires;
    ...
}
```
## zset
```cpp
struct zset{
    // all values value -> score
    dict *dict;
    // 跳表
    zskiplist *zsl;
}
```
## dict
```cpp
struct dict{
    // hashtable 通常只有一个hashtable有值，但是扩容时使用渐进式搬迁，有两个hashtable，依次是旧的和新的
    dicht ht[2];
    ...
}
```
## dictEntry
```cpp
struct dictEntry{
    void *key;
    void *val;
    dictEntry *next;
}
```
## dictht 类似JAVA HashMap 数组+链表
```cpp
struct dictht{
    // 二维
    dictEntry **table;
    // 一维数组长度
    long size;
    // hashtable中元素个数
    long used;
    ...
}
```
## ziplist redis未定义 仅描述所用
```cpp
struct ziplist<T>{
    // 整个压缩列表占用字节数
    int32 zlbytes;
    // 最后一个元素距离列表起始位置的偏移量 用于快速定位最后一个元素，然后倒序遍历
    int32 zltail_offset;
    // 元素个数
    int16 zllength
    //  元素内容列表
    T[] entries;
    // 压缩列表的结束 值恒为0XFF
    int8 zlend;
}
```
## ziplist entry redis未定义 仅描述所用
```cpp
struct entry{
    // 前一个entry的字节长度
    int<var> prevlen
    // 元素类型编码
    int<var> encoding;
    // 元素内容
    optional byte[] content;
}
```
## intset set容纳的元素都是整数且元素个数较小时用到intset
```cpp
struct intset<T>{
    // 元素类型编码 决定整数位宽是16 32 还是64位
    int32 encoding;
    // 元素个数
    int32 length;
    // 元素内容
    int<T> countents[];
}
```
## ziplist_compressed
```cpp
struct ziplist_compressed{
    int32 size;
    byte[] compressed_data;
}
```
## quicklistNode
```cpp
struct ziplist_compressed{
    quicklistNode *prev;
    quicklistNode *next;
    // 指向压缩列表
    ziplist *zl;
    // 压缩列表的字节数
    int32 size;
    // 压缩列表的元素个数
    int16 count;
    // 编码格式 原生字节数组还是LZF压缩存储 RAW==1 LZF==2
    int2 encoding;
    ...
}
```
## quicklist
```cpp
struct ziplist_compressed{
    quicklistNode *head;
    quicklistNode *tail;
    // 元素总数
    long count;
    // quicklistNode个数
    long len;
    // list两端不压缩的节点个数，0表示不开启压缩，1表示首尾节点都不压缩，2表示首两个和尾两个节点不压缩...
    int compress;
}
```
## zskiplistNode
```cpp
struct zskiplistNode {
    // 节点value
    sds ele;
    // 分数
    double score;
    // 回溯指针
    zskiplistNode *backward;
    // 多层连接指针
    struct zskiplistLevel {
        // 前向指针
        struct zskiplistNode *forward;
        // 到下一节点的跨度 可以计算rank
        unsigned long span;
    } level[];
}
```
## zskiplist
```cpp
struct zskiplist {
    // 跳表头指针 尾指针
    zskiplistNode *header, *tail;
    // 跳表长度
    long length;
    // 当前最高层
    int level;
}
```
## listpack 紧凑列表
```cpp
struct listpack<T>{
    // 整个列表占用字节数
    int32 total_bytes;
    // 元素个数
    int16 size
    // 元素内容列表
    T[] entries;
    // 列表的结束 同ziplist一样 值恒为0XFF
    int8 end;
}
```
## lpentry
```cpp
struct lpentry{
    // 元素类型编码
    int<var> encoding;
    // 元素内容
    optional byte[] content;
    // 当前元素长度
    int<var> length;
}
```
## string存储格式
| 存储格式 | 说明 |
| --- | --- |
| embstr | RedisObject和SDS连续存在一起，使用malloc方法一次分配。长度超过44时使用raw。因为字符串的buf都是以\0结尾占用一个字节，RedisObject占16个字节，SDS除开buf最少占3个字节，还剩下44字节 |
| raw | RedisObject和SDS分开存，使用两次malloc |

# string SDS扩容
长度小于1M之前，加倍扩容，超过1M之后，每次扩容只多分配1M
# hashdt扩容
元素个数等于数组长度时，开始扩容，数组长度变为原数组的两倍，但若是redis正在bgsave，为了减少内存页的过多分离
不扩容，但若是元素个数达到了数组长度的5倍，强制扩容
# hashdt缩容
元素个数低于数组长度的10%

