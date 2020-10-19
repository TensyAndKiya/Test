package com.clei.Y2019.M11.D01;

import com.clei.utils.PrintUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderNoTest {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        PrintUtil.dateLine(now.getYear());
        PrintUtil.dateLine(now.getMonthValue());
        PrintUtil.dateLine(now.getDayOfMonth());

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        PrintUtil.dateLine(now.format(df));
    }
}
