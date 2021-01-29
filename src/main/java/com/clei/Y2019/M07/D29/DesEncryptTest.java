package com.clei.Y2019.M07.D29;

import com.clei.utils.Base64Util;
import com.clei.utils.PrintUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DesEncryptTest {

    // 统一字符串的编码
    private static String CHARSET_NAME = "UTF-8";
    // 算法
    private static String ALGORITHM = "DES";
    // key
    private static String KEY = "LINKPARK";

    public static void main(String[] args) throws Exception {
        String str = "abcdefghijklmnop";
        String encryptedStr = encrypt(str);
        PrintUtil.log(encryptedStr);
        String decryptedStr = decrypt(encryptedStr);
        PrintUtil.log(decryptedStr);
    }

    private static String encrypt(String content) throws Exception {
        byte[] keyBytes = KEY.getBytes(CHARSET_NAME);
        DESKeySpec keySpec = new DESKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new SecureRandom());
        byte[] contentBytes = content.getBytes(CHARSET_NAME);
        byte[] result = cipher.doFinal(contentBytes);
        String str = Base64Util.encode(result);
        return str;
    }

    private static String decrypt(String content) throws Exception {
        byte[] keyBytes = KEY.getBytes(CHARSET_NAME);
        DESKeySpec keySpec = new DESKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new SecureRandom());
        byte[] contentBytes = Base64Util.decode(content);
        byte[] result = cipher.doFinal(contentBytes);
        String str = new String(result);
        return str;
    }
}
