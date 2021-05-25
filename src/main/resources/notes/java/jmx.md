# JMX
Java Management Extensions
# 作用
+ 监控应用程序的运行状态和相关统计信息
+ 修改应用程序的配置（无需重启）
+ 状态变化或出错时通知处理
# 基础架构
| 层次 | 描述 |
| --- | --- |
| Instrumentation | 主要包括了一系列的接口定义和描述如何开发MBean的规范。在JMX中MBean代表一个被管理的资源实例，通过MBean中暴露的方法和属性，外界可以获取被管理的资源的状态和操纵MBean的行为 |
| Agent | 用来管理相应的资源，并且为远端用户提供访问的接口。该层的核心是MBeanServer,所有的MBean都要向它注册，才能被管理。注册在MBeanServer上的MBean并不直接和远程应用程序进行通信，他们通过协议适配器（Adapter）和连接器（Connector）进行通信 |
| Distributed | 定义了一系列用来访问Agent的接口和组件，包括Adapter和Connector的描述。注意，Adapter 和Connector的区别在于：Adapter是使用某种Internet协议来与 Agent获得联系，Agent端会有一个对象 (Adapter)来处理有关协议的细节。比如SNMP Adapter和HTTP Adapter。而Connector则是使用类似RPC的方式来访问Agent，在Agent端和客户端都必须有这样一个对象来处理相应的请求与应答。比如RMI Connector。 |
# 使用
+ 定义MBean接口，并编写实现类
+ 注册MBean到MBeanServe
+ 启动程序
+ 使用jconsole连接管理该程序
# 注意
+ 标准MBean名称必需是在要监控的类名后面加上MBean
+ 且要监控的类和MBean接口必需在同一包下
# 示例
见UserMBean

