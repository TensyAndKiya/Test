package com.clei.algorithm.other;

import com.clei.utils.PrintUtil;

/**
 * 斐波那契数列
 *
 * @author KIyA
 */
public class FibonacciSequence {

    public static void main(String[] args) {
        PrintUtil.log(f(100));
    }

    /**
     * 斐波那契数列的第n个数
     * f(1) = 1
     * f(2) = 1
     * f(3) = f(2) + f(1)
     * ...
     *
     * @param n 第n个
     * @return 斐波那契数列的第n个数
     */
    public static long f(int n) {
        if (n < 3) {
            return 1;
        }

        long n1 = 1;
        long n2 = 2;
        for (int i = 3; i < n; i++) {
            long t = n1 + n2;
            n1 = n2;
            n2 = t;
        }
        return n2;
    }
}
