package com.clei.Y2019.M08.D02;

import com.clei.utils.Base64Util;
import com.clei.utils.PrintUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES加密测试
 *
 * @author KIyA
 */
public class DesTest {

    // vector must be 8 bytes long
    private static String vector = "vectorvc";
    private static String charset = "UTF-8";
    // HpZV8BQnhGCdXhqe4WNFpg==
    // HpZV8BQnhGCdXhqe4WNFpg==
    private static String key = "123456781234567812345678";

    public static void main(String[] args) throws Exception {
        String str = "sk中文英文特殊符号！@#￥！@#￥！@#￥";
        PrintUtil.log(str);
        PrintUtil.log(encrypt3DESECB(str));
        PrintUtil.log(encrypt3DES(str));
        PrintUtil.log(encryptDESCBC(str));
        PrintUtil.log(decryptDESCBC(encryptDESCBC(str)));
    }

    private static String encryptDESCBC(String str) throws Exception {
        // key
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(charset));
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
        // 加密向量
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes(charset));
        // 加密
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        byte[] bytes = cipher.doFinal(str.getBytes(charset));
        // 用Base64转成字符串
        return Base64Util.encode(bytes);
    }

    private static String decryptDESCBC(String str) throws Exception {
        // 用Base64转成byte[]
        byte[] bytes = Base64Util.decode(str);
        // key
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(charset));
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
        // 向量
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes(charset));
        // 解密
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte[] result = cipher.doFinal(bytes);
        // 用Base64转成字符串
        return new String(result, charset);
    }

    // ECB 不需要向量
    // key的长度必须大于等于 3*8位
    private static String encrypt3DESECB(String str) throws Exception {
        // key
        DESedeKeySpec desKeySpec = new DESedeKeySpec(key.getBytes(charset));
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
        // 加密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] bytes = cipher.doFinal(str.getBytes(charset));
        // 用Base64转成字符串
        return Base64Util.encode(bytes);
    }

    private static String decrypt3DESECB(String str) throws Exception {
        // 用Base64转成byte[]
        byte[] bytes = Base64Util.decode(str);
        // key
        DESedeKeySpec desKeySpec = new DESedeKeySpec(key.getBytes(charset));
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
        // 解密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] result = cipher.doFinal(bytes);
        // 用Base64转成字符串
        return new String(result, charset);
    }

    public static String encrypt3DES(String str) throws Exception {
        String algorithm = "DESede";
        // key
        SecretKey secretKey = new SecretKeySpec(key.getBytes(charset), algorithm);
        // 加密
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] bytes = cipher.doFinal(str.getBytes(charset));
        // 用Base64转成字符串
        return Base64Util.encode(bytes);
    }

    public static String decrypt3DES(String str) throws Exception {
        // 用Base64转成byte[]
        byte[] bytes = Base64Util.decode(str);
        // key
        String algorithm = "DESede";
        SecretKey secretKey = new SecretKeySpec(key.getBytes(charset), algorithm);
        // 解密
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] result = cipher.doFinal(bytes);
        return new String(result, charset);
    }
}
