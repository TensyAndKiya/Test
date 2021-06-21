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
    T capacity;
    // 数组长度
    T len;
    // 特殊标识位
    byte flags;
    // 数组内容
    byte[] content;
}
```
