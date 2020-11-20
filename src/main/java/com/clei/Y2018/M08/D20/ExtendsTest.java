package com.clei.Y2018.M08.D20;

import com.clei.utils.PrintUtil;

public class ExtendsTest {
    public static void main(String[] args){
        A b=new B();

        /**
         * 总结
         * 1. 父类有多个构造函数时，new 一个子类。。默认调用父类的无参构造。
         * 2. 子类构造器不使用super的话，那么父类必须要有无参构造函数，否则编译报错
         * 3. super和this(使用同类构造函数)都要求在构造函数第一行，所以不能同时使用
         */

    }
}

class A{
    public A(String arg){
        PrintUtil.dateLine("This is arg A");
        PrintUtil.dateLine("arg: " + arg);
    }

    public A(String arg1,String arg2) {
        PrintUtil.dateLine("This is arg 2 A");
        PrintUtil.dateLine("arg1: " + arg1 + "\targ2: " + arg2);
    }

    public A(){
        PrintUtil.dateLine("This is A");
    }
}

class B extends A{
    public B(){
        this("测试一下");
        PrintUtil.dateLine("This is B");

    }

    public B(String arg) {
        PrintUtil.dateLine("This is arg B");
        PrintUtil.dateLine("arg: " + arg);
    }
}
