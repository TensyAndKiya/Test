package com.clei.Y2019.M04.D04;

import com.clei.utils.PrintUtil;

/**
 * @author KIyA
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        String path = BlockingQueueTest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        String[] str = path.split("/");
        StringBuilder configPath = new StringBuilder(path.length());
        for (int i = 0; i < str.length - 1; i++) {
            if (null != str[i] && str[i].length() != 0) {
                configPath.append("/").append(str[i]);
            }
        }
        PrintUtil.log(path);
        PrintUtil.log(configPath.toString() + "/conf/");
    }
}
