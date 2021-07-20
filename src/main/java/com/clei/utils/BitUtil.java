package com.clei.utils;

import java.util.Arrays;

/**
 * 位操作 工具类
 *
 * @author KIyA
 */
public class BitUtil {

    /**
     * 只有一位为1的byte数组
     * 可以用于设置某位为1，通过使用位运算OR
     * 也可以用于判断某位是1或0，通过使用位运算AND
     */
    private static final byte[] SINGLE_BIT_ONE_ARR = new byte[]{-128, 64, 32, 16, 8, 4, 2, 1};

    /**
     * 只有一位为0的byte数组
     * 可以用于设置某位为0，通过使用位运算AND
     * 也可以用于判断某位是1或0，通过使用位运算OR
     */
    private static final byte[] SINGLE_BIT_ZERO_ARR = new byte[]{127, -65, -33, -17, -9, -5, -3, -2};

    public static void main(String[] args) {
        byte b = 0;
        b = set1(b, 0, 2, 4, 6);
        b = set0(b, 1, 3, 5, 7);

        PrintUtil.log(b);
        PrintUtil.log(Integer.toBinaryString(b & 0XFF));

        for (int i = 0; i < 8; i++) {
            PrintUtil.print(getBit(b, i));
        }
    }

    /**
     * 二进制字符串
     *
     * @param b
     * @return 二进制字符串
     */
    public static String toBinaryString(byte b) {
        return Integer.toBinaryString(b & 0XFF);
    }

    /**
     * 八位二进制字符串
     *
     * @param b
     * @return 八位二进制字符串
     */
    public static String toBinaryString8(byte b) {
        String str = toBinaryString(b);
        int max = 8;
        int length = str.length();
        int diff = max - length;
        if (diff > 0) {
            StringBuilder sb = new StringBuilder(max);
            sb.append(str);
            while (diff > 0) {
                sb.insert(0, '0');
                diff--;
            }
            return sb.toString();
        }
        return str;
    }

    /**
     * 取得b的第i位的值
     *
     * @param b 源数据
     * @param i bit位置 0到7
     * @return 0或1
     */
    public static int getBit(byte b, int i) {
        validateI(i);
        return 0 == (b & SINGLE_BIT_ONE_ARR[i]) ? 0 : 1;
        // return -1 == (b | SINGLE_BIT_ZERO_ARR[i]) ? 1 : 0;
    }

    /**
     * 将b的第i位设置v
     *
     * @param b 源数据
     * @param i bit位置 0到7
     * @param v 要设置的值 0或非0 非0一律当做1
     * @return 修改bit位后的值
     */
    public static byte setBit(byte b, int i, int v) {
        return 0 == v ? set0(b, i) : set1(b, i);
    }

    /**
     * 将b的第i位设置1
     *
     * @param b 源数据
     * @param i bit位置 0到7
     * @return 修改bit位后的值
     */
    public static byte set1(byte b, int i) {
        validateI(i);
        return (byte) (b | SINGLE_BIT_ONE_ARR[i]);
    }

    /**
     * 将b的多位设置1
     *
     * @param b    源数据
     * @param args bit索引数组 0到7
     * @return 修改bit位后的值
     */
    public static byte set1(byte b, int... args) {
        for (int i : args) {
            b = set1(b, i);
        }
        return b;
    }

    /**
     * 将b的第i位设置0
     *
     * @param b 源数据
     * @param i bit位置 0到7
     * @return 修改bit位后的值
     */
    public static byte set0(byte b, int i) {
        validateI(i);
        return (byte) (b & SINGLE_BIT_ZERO_ARR[i]);
    }

    /**
     * 将b的多位设置0
     *
     * @param b    源数据
     * @param args bit索引数组 0到7
     * @return 修改bit位后的值
     */
    public static byte set0(byte b, int... args) {
        for (int i : args) {
            b = set0(b, i);
        }
        return b;
    }

    /**
     * 将一个十进制的两位数转为一个8421码的BCD
     *
     * @param i 源数据
     * @return BCD
     */
    public static byte toBCD(int i) {
        if (i < 0 || i > 99) {
            throw new RuntimeException("参数错误 : " + i);
        }
        // 十位
        int a = i / 10;
        // 个位
        int b = i % 10;
        // 结果
        int c = (a << 4) | b;
        return (byte) c;
    }

    /**
     * 将一个8421码的BCD转为一个十进制的两位数
     *
     * @param b 源数据
     * @return int
     */
    public static int BCDToInt(byte b) {
        // 十位
        int a = b >> 4 & 0X0F;
        // 个位
        int aa = b & 0X0F;
        if (a > 9 || aa > 9) {
            throw new RuntimeException("错误的BCD : " + b);
        }
        return a * 10 + aa;
    }

    /**
     * 将四字节的byte数组转为一个有符号的int整型
     *
     * @param data 源数据
     * @return int
     */
    public static int byte2ToSignedInt(byte[] data) {
        if (null == data || 2 != data.length) {
            throw new RuntimeException("参数错误 : " + Arrays.toString(data));
        }
        short a = (short) (data[0] & 0XFF);
        int b = (data[1] & 0XFF);
        return (a << 8) + b;
    }

    /**
     * 验证索引值是否合法
     * 因为是byte i只能是0到7之间
     *
     * @param i bit位置 0到7
     */
    private static void validateI(int i) {
        if (i < 0 || i > 7) {
            throw new RuntimeException("错误的bit索引值: " + i);
        }
    }
}
