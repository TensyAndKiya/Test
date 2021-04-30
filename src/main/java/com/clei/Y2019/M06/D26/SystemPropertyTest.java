package com.clei.Y2019.M06.D26;

import com.clei.utils.PrintUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Set;

import static com.clei.utils.PrintUtil.println;

/**
 * 获得本机的一些信息
 * 获得本地IP地址和主机名
 *
 * @author KIyA
 */
public class SystemPropertyTest {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        println("IP:{}", addr.getHostAddress());
        println("主机名:{}", addr.getHostName());
        println("操作系统:{}", System.getProperty("os.name"));
        println("用户目录:{}", System.getProperty("user.dir"));
        println("系统位数(CPU架构):{}", System.getProperty("os.arch"));
        // 若是Sun/Oracle的JDK
        println("JDK位数:{}", System.getProperty("sun.arch.data.model"));
        //
        PrintUtil.log("System Properties");
        Properties properties = System.getProperties();
        Set<String> set = properties.stringPropertyNames();
        for(String key : set){
            println("KEY:{}\tVALUE:{}",key,properties.getProperty(key));
        }
    }
}
