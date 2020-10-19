package com.clei.utils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * IP工具类
 *
 * @author KIyA
 */
public class IPUtil {

    /**
     * 需要忽略的接口名
     */
    private static String[] ignoredInterfaces = {};

    /**
     * 返回第一个非回环地址 抄的Spring-cloud-common-2.2.2.RELEASE.jar InetUtils源码
     *
     * @return
     */
    public static InetAddress findFirstNonLoopbackAddress() {
        InetAddress result = null;
        try {
            int lowest = Integer.MAX_VALUE;
            for (Enumeration<NetworkInterface> nics = NetworkInterface
                    .getNetworkInterfaces(); nics.hasMoreElements(); ) {
                NetworkInterface ifc = nics.nextElement();
                if (ifc.isUp()) {
                    PrintUtil.dateLine("Testing interface: " + ifc.getDisplayName());
                    if (ifc.getIndex() < lowest || result == null) {
                        lowest = ifc.getIndex();
                    } else if (result != null) {
                        continue;
                    }

                    // @formatter:off
                    if (!ignoreInterface(ifc.getDisplayName())) {
                        for (Enumeration<InetAddress> addrs = ifc
                                .getInetAddresses(); addrs.hasMoreElements(); ) {
                            InetAddress address = addrs.nextElement();

                            PrintUtil.dateLine("displayName : {}, hostName : {}, address : {}, ipV4 : {}, loopBack : {}", ifc.getDisplayName(), address.getHostName(), address.getHostAddress(), (address instanceof Inet4Address), address.isLoopbackAddress());

                            if (address instanceof Inet4Address
                                    && !address.isLoopbackAddress()) {
                                PrintUtil.dateLine("Found non-loopback interface: "
                                        + ifc.getDisplayName());
                                result = address;
                            }
                        }
                    }
                    // @formatter:on
                }
            }
        } catch (IOException ex) {
            PrintUtil.dateLine("Cannot get first non-loopback address", ex);
        }

        if (result != null) {
            return result;
        }

        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            PrintUtil.dateLine("Unable to retrieve localhost");
        }

        return null;
    }

    /**
     * 判断是否忽略
     *
     * @param interfaceName
     * @return
     */
    private static boolean ignoreInterface(String interfaceName) {
        for (String regex : ignoredInterfaces) {
            if (interfaceName.matches(regex)) {
                PrintUtil.dateLine("Ignoring interface: " + interfaceName);
                return true;
            }
        }
        return false;
    }
}
