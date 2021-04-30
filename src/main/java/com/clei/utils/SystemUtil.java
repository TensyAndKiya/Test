package com.clei.utils;

import io.jsonwebtoken.lang.Assert;

import java.util.Scanner;

/**
 * 系统工具类
 *
 * @author KIyA
 */
public class SystemUtil {

    /**
     * scanner
     */
    private final static Scanner INPUT = new Scanner(System.in);

    /**
     * 控制台暂停 直到输入任意字符
     */
    public static void pause() {
        INPUT.next();
    }

    /**
     * 控制台暂停 直到输入指定字符串
     *
     * @param str
     */
    public static void pause(String str) {
        Assert.notNull(str);
        String s;
        while (!str.equals(s = INPUT.nextLine())) {
            PrintUtil.log(s);
        }
    }
}