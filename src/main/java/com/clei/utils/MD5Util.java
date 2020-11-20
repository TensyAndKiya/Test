package com.clei.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * 这个方法
     * str包含了ASCII之外的字符会有问题，如中文字符
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return null;
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
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
            byte[] b = str.getBytes("UTF8");
            md5.update(b, offset, b.length);
            return byteArrayToHexString(md5.digest());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @param b byte[]
     * @return String
     */
    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += byteToHexString(b[i]);
        }
        return result;
    }

    private static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 将字节转换为对应的16进制明文
     *
     * @param b byte
     * @return String
     */
    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
