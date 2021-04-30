package com.clei.Y2019.M06.D29;

import com.clei.utils.PrintUtil;

import java.util.function.Consumer;

import static com.clei.utils.PrintUtil.println;

/**
 * 学习Java8新特性
 * Lambda表达式
 * 方法引用
 * 函数式接口
 * 默认方法
 * Stream
 * Optional
 *
 * @author KIyA
 */
public class Java8Test1 {

    public static void main(String[] args) {

        int c = 110;
        // int a = 33; 不允许同名参数
        // lambda表达式的变量是隐性final的。。不能改值，否则会报错
        // c = 22;
        // 无参可以用()表示就行了。。一个参数可以省略括号 如 k -> k * 2
        MathOperation add = (a, b) -> a + b;
        // 有return语句的话就算只有一行代码也要大括号
        MathOperation subtract = (int a, int b) -> {
            return a - b - c;
        };
        MathOperation multiply = (int a, int b) -> a * b;

        int a = 16, b = 4;

        println("a = {}, b = {}", a, b);
        println("a + b : {}", operation(a, b, add));
        println("a - b : {}", operation(a, b, subtract));
        println("a * b : {}", operation(a, b, multiply));

        Consumer<String> consumer = PrintUtil::log;
        consumer.accept("这个东西也算个lambda吗？");
    }

    private static int operation(int a, int b, MathOperation operation) {
        return operation.operation(a, b);
    }

    interface MathOperation {

        int operation(int a, int b);
    }

}
