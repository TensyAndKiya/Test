# 安装
## 参考
https://nacos.io/zh-cn/docs/quick-start.html
1. git clone https://github.com/alibaba/nacos.git
2. cd nacos/
3. mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U 
4. cd distribution/target/nacos-server-$version/nacos/bin
# 启动
## LINUX
sh startup.sh -m standalone
## WINDOWS
startup.cmd -m standalone
# 访问
http://localhost:8848/nacos/index.html