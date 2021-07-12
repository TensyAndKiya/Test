package com.clei.utils;

/**
 * 数字处理工具
 *
 * @author KIyA
 * @date 2020-06-15
 */
public class NumUtil {

    private final static char[] LOWER_CASE_ARR;

    private final static char[] UPPER_CASE_ARR;

    // 初始化
    static {
        LOWER_CASE_ARR = new char[36];
        UPPER_CASE_ARR = new char[36];

        // 10个数字字符
        for (int i = 0; i < 10; i++) {
            LOWER_CASE_ARR[i] = (char) ('0' + i);
            UPPER_CASE_ARR[i] = (char) ('0' + i);
        }

        // 26个字母字符 分大小写
        for (int i = 0; i < 26; i++) {
            LOWER_CASE_ARR[i + 10] = (char) ('a' + i);
            UPPER_CASE_ARR[i + 10] = (char) ('A' + i);
        }
    }

    /**
     * 数字进制转换
     *
     * @param str
     * @param from
     * @param to
     * @return
     */
    public static String transRadix(String str, int from, int to) {
        // 校验数据合法性
        if (from < 2 || from > 36 || to < 2 || to > 36) {
            throw new RuntimeException("你是沙雕吗？");
        }
        if (!StringUtil.isDigitOrChar(str)) {
            throw new RuntimeException("你是真滴沙雕！");
        }

        // 获取from进制的num
        int num = Integer.parseInt(str, from);

        // 转成to进制的String
        return Integer.toString(num, to);
    }

    /**
     * 转成二级制字符串，32位长度的
     *
     * @param i
     * @return
     */
    public static String toBinaryString(int i) {
        int length = 32;
        String str = Integer.toBinaryString(i);
        // 前面补0
        int diff = length - str.length();
        if (diff > 0) {
            StringBuilder sb = new StringBuilder(length);
            sb.append(str);
            for (int j = 0; j < diff; j++) {
                sb.insert(0, '0');
            }
            return sb.toString();
        }
        return str;
    }

    /**
     * num^(i - 1) + ... + num^0
     *
     * @param num
     * @param i
     * @return
     */
    public static int getPowSum(int num, int i) {
        int sum = 0;
        while (i > 0) {
            sum += getPow(num, --i);
        }
        return sum;
    }

    /**
     * num的i次方 num^i
     *
     * @param num
     * @param i
     * @return
     */
    public static int getPow(int num, int i) {
        if (i < 0) {
            throw new RuntimeException("参数错误");
        }
        int result = 1;
        while (i > 0) {
            result *= num;
            i--;
        }
        return result;
    }

    /**
     * 判断一个整数是否是奇数
     *
     * @param num
     * @return
     */
    public static boolean isOdd(int num) {
        return 1 == (1 & num);
    }

    /**
     * 判断一个整数是否是质数/素数
     * 质数只有两个正因数（1和自己）的自然数即为质数
     * 比1大但不是素数的数称为合数
     * 1和0既非素数也非合数。
     *
     * @param num
     * @return
     */
    public static boolean isPrime(int num) {
        if (num < 2) {
            throw new RuntimeException("参数错误");
        }
        int k = (int) Math.sqrt(num) + 1;
        for (int i = 2; i < k; i++) {
            if (0 == num % i) {
                return false;
            }
        }
        return true;
    }


    /**
     * 阶乘 n!
     *
     * @param n
     */
    public static int factorial(int n) {
        if (n < 2) {
            return 1;
        }
        if (n > 12) {
            throw new RuntimeException("int类型会溢出");
        }
        int result = 1;
        while (n > 1) {
            result *= n;
            n--;
        }
        return result;
    }

    public static void main(String[] args) {
        PrintUtil.log(factorial(12));

        PrintUtil.log(getPowSum(3, 4));

        PrintUtil.log(transRadix("x", 36, 10));
    }
}
