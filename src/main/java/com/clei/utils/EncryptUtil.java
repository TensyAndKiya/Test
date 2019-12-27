package com.clei.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
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
}
