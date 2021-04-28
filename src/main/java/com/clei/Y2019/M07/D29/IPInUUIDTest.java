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
            e.printStackTrace();
            PrintUtil.println("error");
            IP = "127.0.0.1";
        }
        String arr[] = IP.split("[.]");
        for (int i = 0; i < arr.length; i++) {
            String str = arr[i];
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
        String result = PREFIX + uuid.substring(0, uuid.length() - PREFIX.length());
        return result;
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
