package com.clei.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * Base64 util
 *
 * @author KIyA
 */
public class Base64Util {

    public static void main(String[] args) throws Exception {
        String base64Str = fileToBase64("D:\\Picture\\Mary\\Mary_0.png");
        base64ToFile(base64Str, "D:\\files\\xxx.pdf");
    }

    public static String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] decode(String str) {
        return Base64.getDecoder().decode(str);
    }

    /**
     * file -> base64字符串
     *
     * @param filePath
     */
    public static String fileToBase64(String filePath) throws Exception {
        try (InputStream is = new FileInputStream(filePath)) {
            byte[] data = new byte[is.available()];
            is.read(data);
            return encode(data);
        }
    }

    /**
     * base64字符串 -> file
     *
     * @param str      base64字符串
     * @param filePath 输出文件路径
     */
    public static void base64ToFile(String str, String filePath) throws Exception {
        byte[] bytes = decode(str);
        byteToFile(bytes, filePath);
    }

    private static void byteToFile(byte[] bytes, String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(bytes);
        bos.flush();
        bos.close();
        fos.close();
    }
}
