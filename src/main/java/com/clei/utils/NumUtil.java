package com.clei.utils;

/**
 * 数字处理工具
 *
 * @author KIyA
 * @date 2020-06-15
 */
public class NumUtil {

    private final static char[] lCaseArr;

    private final static char[] uCaseArr;

    // 初始化
    static {
        lCaseArr = new char[36];
        uCaseArr = new char[36];

        // 10个数字字符
        for (int i = 0; i < 10; i++) {
            lCaseArr[i] = (char) ('0' + i);
            uCaseArr[i] = (char) ('0' + i);
        }

        // 26个字母字符 分大小写
        for (int i = 0; i < 26; i++) {
            lCaseArr[i + 10] = (char) ('a' + i);
            uCaseArr[i + 10] = (char) ('A' + i);
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

        // 获取10进制的num
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
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(i));
        // 前面补0
        int diff = 32 - sb.length();
        for (int j = 0; j < diff; j++) {
            sb.insert(0, '0');
        }
        return sb.toString();
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

    public static void main(String[] args) {
        System.out.println(getPowSum(3, 4));

        PrintUtil.dateLine(transRadix("x", 36, 10));
    }
}
