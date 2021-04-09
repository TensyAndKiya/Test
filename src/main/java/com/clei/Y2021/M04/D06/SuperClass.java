package com.clei.Y2021.M04.D06;

import com.clei.utils.PrintUtil;

/**
 * 被动引用测试用到的父类
 *
 * @author KIyA
 */
class SuperClass {

    public static int value = 123;

    static {
        PrintUtil.log("SuperClass Init");
    }
}
