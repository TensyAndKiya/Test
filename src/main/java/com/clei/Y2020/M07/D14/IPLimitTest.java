package com.clei.Y2020.M07.D14;

import com.clei.utils.PrintUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 判断给定的ip是否在限制名单内
 *
 * @author KIyA
 */
public class IPLimitTest {

    /**
     * 用ConcurrentHashMap是为了方便使用的同时更改
     * web应用的话可以放到redis里
     */
    private final static ConcurrentHashMap<String, Object> IP_MAP = new ConcurrentHashMap<>();

    private final static Object EMPTY = new Object();

    private final static String SEPARATOR = ";";

    private final static String SLASH = "/";

    private final static char DOT = '.';

    private final static char ASTERISK = '*';

    public static void main(String[] args) {

        PrintUtil.log(System.currentTimeMillis());

        initIPMap("192.168.1.2;192.168.1.1/18;192.168.1.22;192.168.7*");

        String ip = "192.168.7.123";

        PrintUtil.log(isLimitIp(ip));

        PrintUtil.log(System.currentTimeMillis());
    }

    private static void initIPMap(String limitIp) {
        try {
            String[] arr = limitIp.split(SEPARATOR);
            for (String s : arr) {
                int slashIndex = s.indexOf(SLASH);
                if (-1 != slashIndex) {
                    int dotIndex = s.lastIndexOf(DOT);
                    String startNumStr = s.substring(dotIndex + 1, slashIndex);
                    String endNumStr = s.substring(slashIndex + 1);
                    String prefix = s.substring(0, dotIndex + 1);
                    int startNum = Integer.parseInt(startNumStr);
                    int endNum = Integer.parseInt(endNumStr);
                    for (int i = startNum; i < endNum + 1; i++) {
                        IP_MAP.put(prefix + i, EMPTY);
                    }
                } else {
                    IP_MAP.put(s, EMPTY);
                }
            }
        } catch (Exception e) {
            PrintUtil.log("初始化限制IP信息出错");
        }
    }

    private static boolean isLimitIp(String ip) {
        boolean result = false;
        try {
            result = IP_MAP.containsKey(ip);
            if (!result) {
                char[] arr = ip.toCharArray();
                int length = arr.length;
                for (int i = length; i > 0; i--) {
                    arr[i - 1] = ASTERISK;
                    String tempIp = new String(arr, 0, i);
                    result = IP_MAP.containsKey(tempIp);
                    if (result) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            PrintUtil.log("校验IP信息出错");
        }
        return result;
    }
}
