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
```

