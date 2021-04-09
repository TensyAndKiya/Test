package com.clei.Y2021.M04.D06;

import com.clei.utils.PrintUtil;
import com.clei.utils.SystemUtil;

/**
 * 三种被动引用 不会触发类初始化
 * 类加载相关
 *
 * @author KIyA
 */
public class PassiveReferenceTest {

    static {
        // never used
        i = 2;
    }

    static int i = 1;

    public static void main(String[] args) {
        PrintUtil.log(i);
        PrintUtil.log(System.getProperty("java.ext.dirs"));
        // 1 通过子类引用父类的静态字段 不会触发子类的初始化
        PrintUtil.log(SubClass.value);
        // 2 通过数组定义来引用类 不会触发类的初始化
        SubClass[] arr = new SubClass[10];
        PrintUtil.log(arr);
        // 3 使用类的常量 常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化
        PrintUtil.log(SubClass.CONST_VALUE);
        SystemUtil.pause();
    }
}


