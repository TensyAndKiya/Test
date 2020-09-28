package com.clei.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
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
 * 加解密
 */
public class EncryptUtil {
    public final static String CHARSET_UTF8 = "UTF-8";
    private final static String ALGORITHM = "DESede";
    public final static String AES = "AES";
    private final static int BUFFER = 1024;

    // ECB
    public static byte[] encrypt3DES(byte[] bytes,String key,String charset) throws Exception{
        if(null == charset || "".equals(charset)){
            charset = CHARSET_UTF8;
        }
        // key
        SecretKey secretKey = new SecretKeySpec(key.getBytes(charset),ALGORITHM);
        // 加密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);

        return cipher.doFinal(bytes);
    }

    // ECB
    public static byte[] decrypt3DES(byte[] bytes,String key,String charset) throws Exception{
        if(null == charset || "".equals(charset)){
            charset = CHARSET_UTF8;
        }
        // key
        SecretKey secretKey = new SecretKeySpec(key.getBytes(charset),ALGORITHM);
        // 解密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,secretKey);

        return cipher.doFinal(bytes);
    }

    public static byte[] decryptAES(byte[] bytes, String key) throws Exception{
        byte[] enCodeFormat = key.getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] result = cipher.doFinal(bytes);
        return result;
    }

    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        compress(bais,baos);
        data = baos.toByteArray();
        baos.flush();
        baos.close();
        return data;
    }

    public static byte[] deCompress(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        deCompress(bais,baos);
        data = baos.toByteArray();
        baos.flush();
        baos.close();
        return data;
    }

    public static void compress(InputStream is, OutputStream os) throws IOException {
        GZIPOutputStream gos = new GZIPOutputStream(os);
        int length;
        byte[] data = new byte[BUFFER];
        while((length = is.read(data,0,BUFFER)) != -1){
            gos.write(data,0,length);
        }
        gos.finish();
        gos.flush();
        gos.close();
        is.close();
    }

    public static void deCompress(InputStream is,OutputStream os) throws IOException {
        GZIPInputStream gis = new GZIPInputStream(is);
        int length;
        byte[] data = new byte[BUFFER];
        while((length = gis.read(data,0,BUFFER)) != -1){
            os.write(data,0,length);
        }
        gis.close();
        is.close();
    }

    /**
     * md5
     * @param str
     * @return
     * @throws Exception
     */
    public static String md5(String str) throws Exception {
        return md5(str,null,false);
    }

    /**
     * md5
     * @param str 源字符串
     * @param charset 编码
     * @param isUpperCase 返回字符串字母是否大写
     * @throws Exception
     */
    public static String  md5(String str,String charset,boolean isUpperCase) throws Exception{

        MessageDigest md = MessageDigest.getInstance("MD5");

        // 指定编码
        if(null == charset || 0 == charset.length()){
            md.update(str.getBytes());
        }else {
            md.update(str.getBytes(charset));
        }

        byte[] arr = md.digest();

        return byteArrToHexString(arr,isUpperCase);
    }

    /**
     * sha1
     * @param str 源字符串
     * @throws Exception
     */
    public static String sha1(String str) throws Exception{
        return sha1(str,null,false);
    }

    /**
     * sha1
     * @param str 源字符串
     * @param charset 编码
     * @param isUpperCase 返回字符串字母是否大写
     * @throws Exception
     */
    public static String sha1(String str,String charset,boolean isUpperCase) throws Exception{
        // SHA SHA1 SHA-1是一样的
        MessageDigest md = MessageDigest.getInstance("SHA");

        byte[] arr = null;

        // 指定编码
        if(null == charset || 0 == charset.length()){
            arr = md.digest(str.getBytes());
        }else {
            arr = md.digest(str.getBytes(charset));
        }

        return byteArrToHexString(arr,isUpperCase);
    }

    /**
     * 将byte数组转为字符串
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

            str[i * 2] = hd[b >>> 4 & 0xF];
            str[i * 2 + 1] = hd[b & 0xF];
        }

        return new String(str);
    }

    final private static char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    final private static char[] hexUpperDigits = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
}
