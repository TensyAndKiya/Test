package com.clei.Y2019.M11.D01;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderNoTest {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());
        System.out.println(now.getDayOfMonth());

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        System.out.println(now.format(df));
    }
}
