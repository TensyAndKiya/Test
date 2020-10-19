package com.clei.Y2019.M08.D02;

import com.clei.utils.PrintUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Base64Test {
    private static String charset = "UTF-8";
    public static void main(String[] args) throws IOException {
        PrintUtil.dateLine(encode("hasaki"));
    }

    private static String encode(String str) throws UnsupportedEncodingException {
        return new BASE64Encoder().encode(str.getBytes(charset));
    }

    private static String decode(String str) throws IOException {
        byte[] bytes = new BASE64Decoder().decodeBuffer(str);
        return new String(bytes,charset);
    }
}
