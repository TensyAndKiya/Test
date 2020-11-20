package com.clei.Y2019.M04.D03;

import com.clei.utils.PrintUtil;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class DateFormatTest {
    public static void main(String[] args) {
        Date date = new Date();
        PrintUtil.dateLine("date: " + date);
        DateFormat df = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        String dateStr = df.format(date);
        PrintUtil.dateLine("formatStr: " + dateStr);
        try {
            PrintUtil.dateLine("date: " + df.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PrintUtil.dateLine(Integer.MAX_VALUE);
        PrintUtil.dateLine(Long.MAX_VALUE);
        for (int i = 0; i < 10; i++) {
            PrintUtil.dateLine(UUID.randomUUID());
        }
        BigInteger bi = new BigInteger("1");
        BigInteger bi16 = new BigInteger("16");
        for (int i = 0; i < 32; i++) {
            bi = bi.multiply(bi16);
            PrintUtil.dateLine(bi);
        }
        Map<String, Charset> map = Charset.availableCharsets();
        map.forEach((k, v) -> PrintUtil.dateLine("KEY: " + k + "\tVALUE: " + v));
    }
}
