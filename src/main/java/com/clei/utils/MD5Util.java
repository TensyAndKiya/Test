package com.clei.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5 util
 *
 * @author KIyA
 */
public class MD5Util {

    /**
     * 这个方法
     * str包含了ASCII之外的字符会有问题，如中文字符
     *
     * @param str 数据
     * @return 摘要
     */
    public static String md5(String str) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            PrintUtil.log("md5 出错", e);
            return null;
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


    /**
     * 计算消息摘要。
     *
     * @param offset 数据偏移地址。
     * @return 摘要结果。(16字节)
     */
    public static String md5(String str, int offset) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] b = str.getBytes(StandardCharsets.UTF_8);
            md5.update(b, offset, b.length);
            return byteArrayToHexString(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            PrintUtil.log("md5 出错", e);
        }
        return null;
    }

    /**
     * @param b byte[]
     * @return String
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder result = new StringBuilder(b.length * 2);
        for (byte value : b) {
            result.append(byteToHexString(value));
        }
        return result.toString();
    }

    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 将字节转换为对应的16进制明文
     *
     * @param b byte
     * @return String
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }
}
