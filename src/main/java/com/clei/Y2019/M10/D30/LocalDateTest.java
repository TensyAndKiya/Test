package com.clei.Y2019.M10.D30;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTest {
    public static void main(String[] args) {
        String a = "2019-10-30";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate aDate = LocalDate.parse(a,df);
        LocalDate now = LocalDate.now();
        System.out.println(now.isBefore(aDate));

        LocalDateTime ll = LocalDateTime.of(2019,11,1,0,0);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(ll.format(dtf));
        System.out.println(ll.plusMonths(1).minusDays(1).format(dtf));
    }
}
