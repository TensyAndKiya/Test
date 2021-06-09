# 建议的打印GC日志的参数
```
# 必备
## 基本信息
-XX:+PrintGCDetails 
-XX:+PrintGCDateStamps 
## 对象年龄分布
-XX:+PrintTenuringDistribution 
## 堆数据
-XX:+PrintHeapAtGC 
## 引用处理信息
-XX:+PrintReferenceGC 
## 应用停顿时间
-XX:+PrintGCApplicationStoppedTime

# 可选
## 安全点信息
-XX:+PrintSafepointStatistics 
-XX:PrintSafepointStatisticsCount=1

# 日志文件选择
## GC日志输出的文件路径
-Xloggc:/path/to/gc-%t.log
## 开启日志文件分割
-XX:+UseGCLogFileRotation 
## 最多分割几个文件，超过之后从头文件开始写
-XX:NumberOfGCLogFiles=14
## 每个文件上限大小，超过就触发分割
-XX:GCLogFileSize=100M
```