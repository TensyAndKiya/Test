package com.clei.Y2019.M06.D17;

import com.clei.utils.PrintUtil;

import java.util.Date;

public class DropMillsTest {
    public static void main(String[] args) {
        Date date = new Date();
        PrintUtil.log("原来的毫秒数：" + date.getTime());
        dropMills(date);
        PrintUtil.log("现在的毫秒数：" + date.getTime());
    }

    private static void dropMills(final Date date){
        long mills = date.getTime();
        long leftMills = mills%1000;
        if(leftMills > 0){
            date.setTime(mills - leftMills);
        }
    }
}
