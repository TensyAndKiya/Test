package com.clei.jvm;

import com.clei.utils.PrintUtil;

/**
 * StackOverFlowError test
 *
 * @author KIyA
 */
public class StackOverFlowTest {

    private static int stackLength = 1;

    public static void main(String[] args) {
        try {
            callSelf();
        } catch (Throwable t) {
            PrintUtil.log("stack length : {}", stackLength);
            PrintUtil.log("错误信息", t);
        }
    }

    /**
     * 如若设置 -Xss64K
     * java version "1.8.0_261"
     * The stack size specified is too small, Specify at least 108k
     * Error: Could not create the Java Virtual Machine.
     * Error: A fatal exception has occurred. Program will exit.
     */
    private static void callSelf() {
        stackLength++;
        callSelf();
    }
}
