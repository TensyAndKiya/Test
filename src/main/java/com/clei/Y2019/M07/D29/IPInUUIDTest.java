package com.clei.Y2019.M07.D29;

import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 需求是把服务器的IP放入一个32位的uuid中
 * 网关返回时根据IP返回给指定服务器
 *
 * @author KIyA
 */
public class IPInUUIDTest {

    private static String IP;
    private static String PREFIX = "";

    static {
        try {
            IP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            PrintUtil.log("获取本机ip地址出错", e);
            IP = "127.0.0.1";
        }
        String[] arr = IP.split("[.]");
        for (String s : arr) {
            String str = s;
            str = Integer.toHexString(Integer.parseInt(str));
            str = str.length() < 2 ? "G" + str : str;
            PREFIX += str;
        }
    }


    public static void main(String[] args) {
        String str = getIPStr();
        PrintUtil.log("IP:" + IP);
        PrintUtil.log("IPStr:" + str);
        PrintUtil.log("IP:" + getIP(str));
    }

    private static String getIPStr() {
        String uuid = StringUtil.uuid();
        return PREFIX + uuid.substring(0, uuid.length() - PREFIX.length());
    }

    private static String getIP(String IPStr) {
        String ip = "";
        for (int i = 0; i < 4; i++) {
            String str = IPStr.substring(i * 2, i * 2 + 2);
            if (str.startsWith("G")) {
                str = str.substring(1, 2);
            }
            ip += Integer.valueOf(str, 16) + ".";
        }
        ip = ip.substring(0, ip.length() - 1);
        return ip;
    }
}
