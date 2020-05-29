package com.clei.Y2020.M05.D29;

public class Test {
    public static void main(String[] args) {
        Test test = null;

        // 这样访问静态方法或变量 竟然不会报NPE哦
        test.test();
    }

    public static void test(){
        System.out.println("哈哈哈");
    }
}
