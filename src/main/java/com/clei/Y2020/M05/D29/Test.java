package com.clei.Y2020.M05.D29;

import com.clei.utils.PrintUtil;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {

        String s1 = "aa";
        String s2 = "aa";

        PrintUtil.log("s1 == s2 : " + (s1 == s2));

        Test test = null;

        // 这样访问静态方法或变量 竟然不会报NPE哦
        test.test();

        ymd();
    }

    public static void test(){
        PrintUtil.log("哈哈哈");
    }

    /**
     * 给一个 年月 第几周的第几天
     * 算出具体日期
     */
    public static void ymd(){

        int year = 2020;

        int month = 3;

        int weekOfMonth = 3;

        int dayOfWeek = 2;

        LocalDate date = LocalDate.of(year,month,1);

        LocalDate monthEnd = date.plusMonths(1).minusDays(1);

        int dayWeek = date.getDayOfWeek().getValue();

        int dayDiff = (weekOfMonth - 1) * 7 + dayOfWeek - dayWeek;

        LocalDate targetDate = date.plusDays(dayDiff);

        if(dayDiff < 0 || targetDate.isAfter(monthEnd)){
            PrintUtil.log(0);
        }else {
            PrintUtil.log(targetDate);
        }

    }
}
