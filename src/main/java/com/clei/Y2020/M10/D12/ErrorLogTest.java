package com.clei.Y2020.M10.D12;

import com.clei.utils.PrintUtil;

/**
 * 测试下自己的异常日志答应
 *
 * @author KIyA
 */
public class ErrorLogTest {

    public static void main(String[] args) {
        try {
            method4();
        } catch (Exception e) {
            PrintUtil.println("cause : {}", e.getCause());
            PrintUtil.println("getLocalizedMessage : {}", e.getLocalizedMessage());
            PrintUtil.println("message : {}", e.getMessage());
            PrintUtil.println("toString : {}", e.toString());

            e.printStackTrace();

            PrintUtil.println("custom begin.");
            PrintUtil.println(e.toString());
            for (StackTraceElement s : e.getStackTrace()) {
                PrintUtil.println("\t at " + s.toString());
            }
            for (Throwable se : e.getSuppressed()) {
                PrintUtil.println(se.toString());
            }
            if (null != e.getCause()) {
                PrintUtil.println(e.getCause().toString());
            }
            PrintUtil.println("custom begin.");

            PrintUtil.println("log");
            PrintUtil.log("竟然有错误 ：{}", "卧槽", e);

        }
    }

    private static void method1() {
        String str = "hasaki";
        int i = Integer.parseInt(str);
        PrintUtil.print(i);
    }

    private static void method2() {
        method1();
    }

    private static void method3() {
        method2();
    }

    private static void method4() {
        method3();
    }

}
