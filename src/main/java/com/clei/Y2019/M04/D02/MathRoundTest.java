package com.clei.Y2019.M04.D02;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MathRoundTest {
    public static void main(String[] args) {
        double d = 123.1455858123D;
        //method 1
        BigDecimal bd = new BigDecimal(String.valueOf(d));
        System.out.println(bd);
        System.out.println(bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        //method 2
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(bd.doubleValue()));
        //method 3
        System.out.println(Math.round(d*100)/100f);
    }
}
