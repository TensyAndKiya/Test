package com.clei.Y2019.M11.D01;

import com.clei.utils.PrintUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderNoTest {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        PrintUtil.log(now.getYear());
        PrintUtil.log(now.getMonthValue());
        PrintUtil.log(now.getDayOfMonth());

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        PrintUtil.log(now.format(df));
    }
}
