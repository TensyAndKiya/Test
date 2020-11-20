package com.clei.Y2019.M06.D15;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;

import java.time.LocalDateTime;

public class DateUtilsTest {
    public static void main(String[] args) {
        PrintUtil.dateLine(System.currentTimeMillis());
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateStr = DateUtil.format(localDateTime);
        LocalDateTime localDateTime1 = DateUtil.parse(dateStr);
        PrintUtil.dateLine(dateStr);
        PrintUtil.dateLine(localDateTime1);
        PrintUtil.dateLine(DateUtil.toEpochSecond(localDateTime));
        PrintUtil.dateLine(DateUtil.toEpochMilli(localDateTime));
        PrintUtil.dateLine(DateUtil.toInstant(localDateTime).toEpochMilli());
    }
}
