package com.clei.Y2019.M06.D15;

import com.clei.utils.DateUtil;

import java.time.LocalDateTime;

public class DateUtilsTest {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateStr = DateUtil.format(localDateTime);
        LocalDateTime localDateTime1 = DateUtil.parse(dateStr);
        System.out.println(dateStr);
        System.out.println(localDateTime1);
        System.out.println(DateUtil.toEpochSecond(localDateTime));
        System.out.println(DateUtil.toEpochMilli(localDateTime));
        System.out.println(DateUtil.toInstant(localDateTime).toEpochMilli());
    }
}
