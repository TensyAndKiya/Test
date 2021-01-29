package com.clei.Y2019.M08.D02;

import com.clei.utils.Base64Util;
import com.clei.utils.PrintUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Base64Test {
    private static String charset = "UTF-8";
    public static void main(String[] args) throws IOException {
        PrintUtil.log(encode("hasaki"));
    }

    private static String encode(String str) throws UnsupportedEncodingException {
        return Base64Util.encode(str.getBytes(charset));
    }

    private static String decode(String str) throws IOException {
        byte[] bytes = Base64Util.decode(str);
        return new String(bytes,charset);
    }
}
