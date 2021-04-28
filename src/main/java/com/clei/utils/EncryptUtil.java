package com.clei.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 加解密 util
 *
 * @author KIyA
 */
public class EncryptUtil {

    /**
     * ECB 不需要向量  key的长度必须大于等于 3*8位
     * ECB直接用 DESede也行
     */
    private final static String ALGORITHM_3DES_ECB = "DESede/ECB/PKCS5Padding";
    private final static String ALGORITHM_3DES_CBC = "DESede/CBC/PKCS5Padding";
    private final static String ALGORITHM_DES = "DES/ECB/PKCS5Padding";
    public final static String ALGORITHM_AES = "AES";
    private final static int BUFFER = 1024;

    final private static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    final private static char[] hexUpperDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 3DES ECB 加密
     *
     * @param bytes 要加密的数据
     * @param key   加密key
     * @return 加密后的数据
     * @throws Exception
     */
    public static byte[] encrypt3DES(byte[] bytes, String key) throws Exception {
        return encrypt(ALGORITHM_3DES_ECB, bytes, key, null);
    }

    /**
     * 3DES CBC 加密
     *
     * @param bytes  要加密的数据
     * @param key    加密key
     * @param vector 向量
     * @return 加密后的数据
     * @throws Exception
     */
    public static byte[] encrypt3DESCBC(byte[] bytes, String key, String vector) throws Exception {
        return encrypt(ALGORITHM_3DES_CBC, bytes, key, vector);
    }

    /**
     * DES 加密
     *
     * @param bytes 要加密的数据
     * @param key   加密key
     * @return 加密后的数据
     * @throws Exception
     */
    public static byte[] encryptDES(byte[] bytes, String key) throws Exception {
        return encrypt(ALGORITHM_DES, bytes, key, null);
    }

    /**
     * AES 加密
     *
     * @param bytes 要加密的数据
     * @param key   加密key
     * @return 加密后的数据
     * @throws Exception
     */
    public static byte[] encryptAES(byte[] bytes, String key) throws Exception {
        return encrypt(ALGORITHM_AES, bytes, key, null);
    }

    /**
     * 3DES ECB 解密
     *
     * @param bytes 要解密的数据
     * @param key   解密key
     * @return 解密后的数据
     * @throws Exception
     */
    public static byte[] decrypt3DES(byte[] bytes, String key) throws Exception {
        return decrypt(ALGORITHM_3DES_ECB, bytes, key, null);
    }

    /**
     * 3DES CBC 解密
     *
     * @param bytes  要解密的数据
     * @param key    解密key
     * @param vector 向量
     * @return 解密后的数据
     * @throws Exception
     */
    public static byte[] decrypt3DESCBC(byte[] bytes, String vector, String key) throws Exception {
        return decrypt(ALGORITHM_3DES_CBC, bytes, key, vector);
    }

    /**
     * DES 解密
     *
     * @param bytes 要解密的数据
     * @param key   解密key
     * @return 解密后的数据
     * @throws Exception
     */
    public static byte[] decryptDES(byte[] bytes, String key) throws Exception {
        return decrypt(ALGORITHM_DES, bytes, key, null);
    }

    /**
     * AES 解密
     *
     * @param bytes 要解密的数据
     * @param key   解密key
     * @return 解密后的数据
     * @throws Exception
     */
    public static byte[] decryptAES(byte[] bytes, String key) throws Exception {
        return decrypt(ALGORITHM_AES, bytes, key, null);
    }

    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        compress(bais, baos);
        data = baos.toByteArray();
        baos.flush();
        baos.close();
        return data;
    }

    public static byte[] deCompress(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        deCompress(bais, baos);
        data = baos.toByteArray();
        baos.flush();
        baos.close();
        return data;
    }

    /**
     * md5
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String md5(String str) throws Exception {
        return md5(str, null, false);
    }

    /**
     * md5
     *
     * @param str         源字符串
     * @param charset     编码
     * @param isUpperCase 返回字符串字母是否大写
     * @throws Exception
     */
    public static String md5(String str, String charset, boolean isUpperCase) throws Exception {

        MessageDigest md = MessageDigest.getInstance("MD5");

        // 指定编码
        if (StringUtil.isBlank(charset)) {
            md.update(str.getBytes());
        } else {
            md.update(str.getBytes(charset));
        }

        byte[] arr = md.digest();

        return byteArrToHexString(arr, isUpperCase);
    }

    /**
     * sha1
     *
     * @param str 源字符串
     * @throws Exception
     */
    public static String sha1(String str) throws Exception {
        return sha1(str, null, false);
    }

    /**
     * sha1
     *
     * @param str         源字符串
     * @param charset     编码
     * @param isUpperCase 返回字符串字母是否大写
     * @throws Exception
     */
    public static String sha1(String str, String charset, boolean isUpperCase) throws Exception {
        // SHA SHA1 SHA-1是一样的
        MessageDigest md = MessageDigest.getInstance("SHA");

        byte[] arr;

        // 指定编码
        if (StringUtil.isBlank(charset)) {
            arr = md.digest(str.getBytes());
        } else {
            arr = md.digest(str.getBytes(charset));
        }

        return byteArrToHexString(arr, isUpperCase);
    }

    /**
     * 将byte数组转为字符串
     *
     * @param arr
     * @param isUpperCase
     * @return
     */
    public static String byteArrToHexString(byte[] arr, boolean isUpperCase) {
        char[] hd = isUpperCase ? hexUpperDigits : hexDigits;

        int length = arr.length;

        char[] str = new char[length * 2];

        for (int i = 0; i < length; i++) {
            byte b = arr[i];

            // 一个byte变两个char，前4位和后四位各一个
            str[i * 2] = hd[b >>> 4 & 0xF];
            str[i * 2 + 1] = hd[b & 0xF];
        }

        return new String(str);
    }

    private static void compress(InputStream is, OutputStream os) throws IOException {
        GZIPOutputStream gos = new GZIPOutputStream(os);
        int length;
        byte[] data = new byte[BUFFER];
        while ((length = is.read(data, 0, BUFFER)) != -1) {
            gos.write(data, 0, length);
        }
        gos.finish();
        gos.flush();
        gos.close();
        is.close();
    }

    private static void deCompress(InputStream is, OutputStream os) throws IOException {
        GZIPInputStream gis = new GZIPInputStream(is);
        int length;
        byte[] data = new byte[BUFFER];
        while ((length = gis.read(data, 0, BUFFER)) != -1) {
            os.write(data, 0, length);
        }
        gis.close();
        is.close();
    }

    /**
     * 加密
     *
     * @param algorithm 算法
     * @param bytes     要加密的数据
     * @param key       加密key
     * @param vector    向量
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(String algorithm, byte[] bytes, String key, String vector) throws Exception {
        // key algorithm有时需要用短的 DES DESede等
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), algorithm);
        // 加密
        Cipher cipher = Cipher.getInstance(algorithm);
        if (StringUtil.isNotBlank(vector)) {
            IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }
        return cipher.doFinal(bytes);
    }

    /**
     * 解密
     *
     * @param algorithm 算法
     * @param bytes     要解密的数据
     * @param key       解密key
     * @param vector    向量
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(String algorithm, byte[] bytes, String key, String vector) throws Exception {
        byte[] enCodeFormat = key.getBytes();
        // key algorithm有时需要用短的 DES DESede等
        SecretKey secretKey = new SecretKeySpec(enCodeFormat, algorithm);
        // 解密
        Cipher cipher = Cipher.getInstance(algorithm);
        if (StringUtil.isNotBlank(vector)) {
            IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }
        return cipher.doFinal(bytes);
    }
}
