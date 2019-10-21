package com.clei.Y2019.M06.D03;

public class FloatTest {
    public static void main(String[] args) {
        double d =2.1474836E7;
        int i = (int) d;
        float f = 2.1474836E7f;
        System.out.println(i);

        System.out.println("FLOAT_MAX:" + Float.MAX_VALUE);
        System.out.println("FLOAT_MIN:" + Float.MIN_VALUE);
        System.out.println("INTEGER_MAX:" + Integer.MAX_VALUE);
        System.out.println("INTEGER_MIN:" + Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE/100);
        System.out.println(Integer.MAX_VALUE/100 == f);
    }
}
