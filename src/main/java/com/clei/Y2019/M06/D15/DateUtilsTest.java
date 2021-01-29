package com.clei.Y2019.M06.D15;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;

import java.time.LocalDateTime;

public class DateUtilsTest {
    public static void main(String[] args) {
        PrintUtil.log(System.currentTimeMillis());
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateStr = DateUtil.format(localDateTime);
        LocalDateTime localDateTime1 = DateUtil.parse(dateStr);
        PrintUtil.log(dateStr);
        PrintUtil.log(localDateTime1);
        PrintUtil.log(DateUtil.toEpochSecond(localDateTime));
        PrintUtil.log(DateUtil.toEpochMilli(localDateTime));
        PrintUtil.log(DateUtil.toInstant(localDateTime).toEpochMilli());
    }
}
