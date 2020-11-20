package com.clei.algorithm.other;

/**
 * 欧几里得算法，辗转相除法
 *
 * @author KIyA
 */
public class Euclid {

    public static void main(String[] args) {
        System.out.println(getGCD(-24, -18));
    }

    /**
     * 获得最大公约数 greatest common divisor
     *
     * @return
     */
    public static int getGCD(int a, int b) {
        if (a == 0 || b == 0) {
            throw new RuntimeException("参数错误");
        }
        if (a < 0) {
            a = -a;
        }
        if (b < 0) {
            b = -b;
        }
        if (a == b) {
            return a;
        }
        if (a > b) {
            return get(a, b);
        } else {
            return get(b, a);
        }
    }

    private static int get(int a, int b) {
        int mod = a % b;
        while (mod != 0) {
            a = b;
            b = mod;
            mod = a % b;
        }
        return b;
    }
}
