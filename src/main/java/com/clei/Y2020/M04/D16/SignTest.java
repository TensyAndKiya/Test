package com.clei.Y2020.M04.D16;

import com.clei.utils.PrintUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SignTest {

    public static void main(String[] args) {

        PrintUtil.dateLine(new BigDecimal("9.567").setScale(2, RoundingMode.CEILING).toString());

        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        LocalDateTime entranceTime = LocalDateTime.ofInstant(instant, ZoneId.of("GMT+8"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        PrintUtil.dateLine(entranceTime.format(dtf));

        String json = "{'key':'value','key2':'value2'}";

        PrintUtil.dateLine(getSHA256StrJava(json));
    }


    /**
     * 　* 利用java原生的摘要实现SHA256加密
     * 　* @param str 加密后的报文
     * 　* @return
     *
     */
    public static String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 　* 将byte转为16进制
     * 　* @param bytes
     * 　* @return
     *
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
