package com.clei.Y2020.M06.D04;

import com.clei.utils.PrintUtil;

import java.lang.reflect.Method;

/**
 * Bootstrap ClassLoader
 * Extension Classloader
 * Application ClassLoader
 *
 * @author KIyA
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception{

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        PrintUtil.dateLine(classLoader);

        ClassLoader parent = classLoader.getParent();

        PrintUtil.dateLine(parent);

        ClassLoader grandFather = parent.getParent();

        // 找不到ExtClassLoader的父Loader，
        // 原因是Bootstrap Loader（引导类加载器）是用C语言实现的，
        // 找不到一个确定的返回父Loader的方式，于是就返回null
        PrintUtil.dateLine(grandFather);

        // rt.jar
        PrintUtil.dateLine(new String().getClass().getClassLoader());

        // 类加载的三种方式
        ClassLoader cl = ClassLoaderTest.class.getClassLoader();

        PrintUtil.dateLine(cl == classLoader);

        // 1.不会执行初始化块
        // cl.loadClass("com.clei.Y2020.M06.D04.UnLoad");

        // 2.默认执行初始化块
        // Class.forName("com.clei.Y2020.M06.D04.UnLoad");

        // 3.指定是否执行执行初始化块
        Class.forName("com.clei.Y2020.M06.D04.UnLoad",false,cl);

        // 双亲委派原则

        // 当一个类加载器收到类加载任务，
        // 会先交给其父类加载器去完成，
        // 因此最终加载任务都会传递到顶层的启动类加载器，
        // 只有当父类加载器无法完成加载任务时，
        // 才会尝试执行加载任务

        // 双亲委派原则 好处

        // 1. 可以避免重复加载，父类已经加载了，子类就不需要再次加载
        // 2. 更加安全，很好的解决了各个类加载器的基础类的统一问题，
        //    如果不使用该种方式，那么用户可以随意定义类加载器来加载核心api，
        //    会带来相关隐患。

        // 自定义类加载器使用
        MyClassLoader myClassLoader = new MyClassLoader("E:\\TEMP");

        Class clazz = myClassLoader.loadClass("ClassLoaderTestTest");

        Object object = clazz.newInstance();

        Method testtest = clazz.getDeclaredMethod("testtest");

        Method test = clazz.getDeclaredMethod("test");

        testtest.invoke(object);

        test.invoke(object);

        PrintUtil.dateLine(object);
    }

}
