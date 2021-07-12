package com.clei.utils;

/**
 * 位操作 工具类
 *
 * @author KIyA
 */
public class BitUtil {

    /**
     * 设置某位为1的操作数数组
     * 通过使用位运算OR
     */
    private static final byte[] OPERAND_OR_ARR = new byte[]{-128, 64, 32, 16, 8, 4, 2, 1};

    /**
     * 设置某位为0的操作数数组
     * 通过使用位运算AND
     */
    private static final byte[] OPERAND_AND_ARR = new byte[]{127, -65, -33, -17, -9, -5, -3, -2};

    public static void main(String[] args) {
        byte b = 0;
        b = set1(b, 0);
        b = set1(b, 2);
        b = set1(b, 4);
        b = set1(b, 6);

        b = set0(b, 1);
        b = set0(b, 3);
        b = set0(b, 5);
        b = set0(b, 7);

        PrintUtil.log(b);
        PrintUtil.log(Integer.toBinaryString(b & 0XFF));
    }

    /**
     * 将b的第i位设置v
     *
     * @param b 源数据
     * @param i bit位置 0到7
     * @param v 要设置的值 0或1
     * @return
     */
    public static byte setBit(byte b, int i, int v) {
        return 0 == v ? set0(b, i) : set1(b, i);
    }

    /**
     * 将b的第i位设置1
     *
     * @param b 源数据
     * @param i bit位置 0到7
     * @return
     */
    public static byte set1(byte b, int i) {
        return (byte) (b | OPERAND_OR_ARR[i]);
    }

    /**
     * 将b的第i位设置0
     *
     * @param b 源数据
     * @param i bit位置 0到7
     * @return
     */
    public static byte set0(byte b, int i) {
        return (byte) (b & OPERAND_AND_ARR[i]);
    }
}
