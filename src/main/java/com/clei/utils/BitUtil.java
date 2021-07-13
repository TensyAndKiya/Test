package com.clei.utils;

/**
 * 位操作 工具类
 *
 * @author KIyA
 */
public class BitUtil {

    /**
     * 只有一位为1的数组
     * 可以用于设置某位为1，通过使用位运算OR
     * 也可以用于判断某位是1或0，通过使用位运算AND
     */
    private static final byte[] SINGLE_BIT_ONE_ARR = new byte[]{-128, 64, 32, 16, 8, 4, 2, 1};

    /**
     * 只有一位为0的数组
     * 可以用于设置某位为0，通过使用位运算AND
     * 也可以用于判断某位是1或0，通过使用位运算OR
     */
    private static final byte[] SINGLE_BIT_ZERO_ARR = new byte[]{127, -65, -33, -17, -9, -5, -3, -2};

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

        for (int i = 0; i < 8; i++) {
            PrintUtil.print(getBit(b, i));
        }
    }

    /**
     * 取得b的第i位的值
     *
     * @param b 源数据
     * @param i bit位置 0到7
     * @return 0或1
     */
    public static int getBit(byte b, int i) {
        return 0 == (b & SINGLE_BIT_ONE_ARR[i]) ? 0 : 1;
        // return -1 == (b | SINGLE_BIT_ZERO_ARR[i]) ? 1 : 0;
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
        return (byte) (b | SINGLE_BIT_ONE_ARR[i]);
    }

    /**
     * 将b的第i位设置0
     *
     * @param b 源数据
     * @param i bit位置 0到7
     * @return
     */
    public static byte set0(byte b, int i) {
        return (byte) (b & SINGLE_BIT_ZERO_ARR[i]);
    }
}
