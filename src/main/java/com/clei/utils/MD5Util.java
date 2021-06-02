package com.clei.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * md5 util
 *
 * @author KIyA
 */
public class MD5Util {

    /**
     * MD5算法名称
     */
    private final static String ALGORITHM_MD5 = "MD5";

    /**
     * SHA算法名称
     */
    private final static String ALGORITHM_SHA = "SHA";

    /**
     * SHA256算法名称
     */
    private final static String ALGORITHM_SHA256 = "SHA-256";

    /**
     * 16进制字符数组
     */
    private static final char[] HEX_CHAR_ARR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 这个方法
     * str包含了ASCII之外的字符会跟其它方法生成的不一样，如中文字符
     *
     * @param str 数据
     * @return 摘要
     */
    public static String md55(String str) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance(ALGORITHM_MD5);
        } catch (Exception e) {
            PrintUtil.log("{} 出错", ALGORITHM_MD5, e);
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
     * 计算md5
     *
     * @param str 要计算的字符串
     * @return md5值
     */
    public static String md5(String str) {
        return checksum(str, ALGORITHM_MD5);
    }

    /**
     * 计算md5
     *
     * @param inputStream 数据流
     * @return md5值
     */
    public static String md5(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        int length;
        try {
            MessageDigest md5 = MessageDigest.getInstance(ALGORITHM_MD5);
            while (-1 != (length = inputStream.read(buffer))) {
                md5.update(buffer, 0, length);
            }
            return byteArrToHexString(md5.digest());
        } catch (Exception e) {
            PrintUtil.log("{} 出错", ALGORITHM_MD5, e);
            return null;
        }
    }

    /**
     * 计算md5
     *
     * @param file 文件
     * @return md5值
     */
    public static String md5(File file) {
        String str = null;
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            str = md5(bis);
        } catch (Exception e) {
            PrintUtil.log("{} 出错", ALGORITHM_MD5, e);
        }
        return str;
    }

    /**
     * sha
     *
     * @param str 要计算的字符串
     * @return sha值
     */
    public static String sha(String str) {
        // SHA SHA1 SHA-1是一样的
        return checksum(str, ALGORITHM_SHA);
    }

    /**
     * sha256
     *
     * @param str 要计算的字符串
     * @return sha256值
     */
    public static String sha256(String str) {
        return checksum(str, ALGORITHM_SHA256);
    }

    /**
     * sha256
     *
     * @param inputStream 数据流
     * @return md5值
     */
    public static String sha256(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        int length;
        try {
            MessageDigest md5 = MessageDigest.getInstance(ALGORITHM_SHA256);
            while (-1 != (length = inputStream.read(buffer))) {
                md5.update(buffer, 0, length);
            }
            return byteArrToHexString(md5.digest());
        } catch (Exception e) {
            PrintUtil.log("{} 出错", ALGORITHM_SHA256, e);
            return null;
        }
    }

    /**
     * 计算md5
     *
     * @param file 文件
     * @return md5值
     */
    public static String sha256(File file) {
        String str = null;
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            str = sha256(bis);
        } catch (Exception e) {
            PrintUtil.log("{} 出错", ALGORITHM_SHA256, e);
        }
        return str;
    }

    /**
     * checksum
     *
     * @param str       要计算的字符串
     * @param algorithm 使用的算法
     */
    private static String checksum(String str, String algorithm) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] data = str.getBytes(StandardCharsets.UTF_8);
            byte[] arr = md.digest(data);
            result = byteArrToHexString(arr);
        } catch (Exception e) {
            PrintUtil.log("{} 出错", algorithm, e);
        }
        return result;
    }


    /**
     * 将byte数组转为字符串
     *
     * @param bArr byte数组
     * @return byte数组转的16进制字符串
     */
    private static String byteArrToHexString(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length * 2];
        for (int i = 0; i < length; i++) {
            byte b = bArr[i];
            // 一个byte变两个char，高四位低四位各一个
            cArr[i * 2] = HEX_CHAR_ARR[b >>> 4 & 0xF];
            cArr[i * 2 + 1] = HEX_CHAR_ARR[b & 0xF];
        }
        return new String(cArr);
    }
}
