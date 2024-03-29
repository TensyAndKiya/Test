package com.clei.Y2019.M06.D29;

import com.clei.utils.PrintUtil;

/**
 * 学习Java8新特性
 *
 * @author KIyA
 */
public class Java8Test4 {

    public static void main(String[] args) {
        Dog dog = () -> PrintUtil.log("汪汪汪");
        dog.run();
        Dog.protect();
        dog.bark();
        dog.eat();
    }
}

/**
 * 函数式接口 只能有一个抽象方法 但是可以有默认方法 和 静态方法
 */
@FunctionalInterface
interface Dog {

    void bark();

    // 取消注释会报错
    // void bite();

    // 默认方法
    default void run() {
        PrintUtil.log("跑起来");
    }

    default void eat() {
        PrintUtil.log("开饭啦");
    }

    // 静态方法
    static void protect() {
        PrintUtil.log("保护朋友");
    }
}
