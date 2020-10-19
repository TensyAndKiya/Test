package com.clei.Y2019.M10.D30;

import com.clei.utils.PrintUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalDateTest {
    public static void main(String[] args) {
        String a = "2019-10-15";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate aDate = LocalDate.parse(a, df);
        LocalDate now = LocalDate.now();

        PrintUtil.dateLine("before : " + now.isBefore(now));
        PrintUtil.dateLine("after : " + now.isAfter(now));


        PrintUtil.dateLine("差异1 ： " + ChronoUnit.DAYS.between(now, aDate));

        LocalDateTime ll = LocalDateTime.of(2019, 11, 1, 0, 0);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        String date1 = "2019-11-14 11:11:11";
        String date2 = "2019-11-14 11:11:12";

        PrintUtil.dateLine("差异 ： " + ChronoUnit.SECONDS.between(LocalDateTime.parse(date1, dtf), LocalDateTime.parse(date2, dtf)));
        PrintUtil.dateLine("差异 ： " + ChronoUnit.DAYS.between(LocalDateTime.parse(date1, dtf), LocalDateTime.parse(date2, dtf)));


        PrintUtil.dateLine(ll.format(dtf));
        PrintUtil.dateLine(ll.plusMonths(1).minusDays(1).format(dtf));
    }
}
