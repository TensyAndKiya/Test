package com.clei.jmx;

import com.clei.jmx.mbean.User;
import com.clei.utils.PrintUtil;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.TimeUnit;

/**
 * JMX test
 *
 * @author KIyA
 * @date 2021-05-25
 */
public class JMXTest {

    public static void main(String[] args) throws Exception {
        remote();
    }

    /**
     * 本地连接
     *
     * @throws Exception
     */
    private static void local() throws Exception {
        // 设置MBean对象名,格式为：“域名：type=MBean类型,name=MBean名称”
        String jmxName = "com.clei.jmx.mbean:type=user,name=user001";
        // 获得MBeanServer
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        // 创建ObjectName
        ObjectName objectName = new ObjectName(jmxName);
        // 创建并注册MBean
        User user = new User();
        server.registerMBean(user, objectName);

        while (true) {
            TimeUnit.SECONDS.sleep(10L);
            PrintUtil.log(user.toString());
        }
    }

    /**
     * 远程连接
     *
     * @throws Exception
     */
    private static void remote() throws Exception {
        // 设置MBean对象名,格式为：“域名：type=MBean类型,name=MBean名称”
        String jmxName = "com.clei.jmx.mbean:type=user,name=user001";
        // 获得MBeanServer
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        // 创建ObjectName
        ObjectName objectName = new ObjectName(jmxName);
        // 创建并注册MBean
        User user = new User();
        server.registerMBean(user, objectName);

        final int port = 8888;
        // 注册一个端口
        LocateRegistry.createRegistry(port);
        // URL路径的结尾可以随意指定，但如果需要用Jconsole来进行连接，则必须使用jmxrmi
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + port + "/jmxrmi");
        JMXConnectorServer jcs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
        jcs.start();

        while (true) {
            TimeUnit.SECONDS.sleep(10L);
            PrintUtil.log(user.toString());
        }
    }
}
