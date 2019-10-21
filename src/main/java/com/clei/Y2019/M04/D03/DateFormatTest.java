package com.clei.Y2019.M04.D03;

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
        System.out.println("date: "+date);
        DateFormat df = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        String dateStr = df.format(date);
        System.out.println("formatStr: "+dateStr);
        try {
            System.out.println("date: "+df.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID());
        }
        BigInteger bi = new BigInteger("1");
        BigInteger bi16 = new BigInteger("16");
        for (int i = 0; i < 32; i++) {
            bi = bi.multiply(bi16);
            System.out.println(bi);
        }
        Map<String,Charset> map = Charset.availableCharsets();
        map.forEach((k,v)->System.out.println("KEY: "+ k+"\tVALUE: "+v));
    }
}
