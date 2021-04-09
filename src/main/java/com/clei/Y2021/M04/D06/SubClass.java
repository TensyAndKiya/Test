package com.clei.Y2021.M04.D06;

import com.clei.utils.PrintUtil;

/**
 * 被动引用测试用到的子类
 *
 * @author KIyA
 */
class SubClass extends SuperClass {

    public final static int CONST_VALUE = 321;

    static {
        PrintUtil.log("SubClass Init");
    }
}
