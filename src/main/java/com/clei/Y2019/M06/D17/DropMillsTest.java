package com.clei.Y2019.M06.D17;

import java.util.Date;

public class DropMillsTest {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println("原来的毫秒数：" + date.getTime());
        dropMills(date);
        System.out.println("现在的毫秒数：" + date.getTime());
    }

    private static void dropMills(final Date date){
        long mills = date.getTime();
        long leftMills = mills%1000;
        if(leftMills > 0){
            date.setTime(mills - leftMills);
        }
    }
}
