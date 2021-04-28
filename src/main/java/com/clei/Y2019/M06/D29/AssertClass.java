package com.clei.Y2019.M06.D29;

import static com.clei.utils.PrintUtil.println;

/**
 * 断言 了解一波
 * 断言在默认情况下是关闭的
 * 要启用断言请使用-ea参数
 * 禁用断言使用-dsa参数
 *
 * @author KIyA
 */
public class AssertClass {

    public static void main(String[] args) {
        println("获取一个随机数");
        double i = Math.random() * 100;
        println("随机数为：{}", i);
        assert i > 64 : "竟然不大于32，爆炸！" + i;
        println("成功！");
    }
}
