package com.clei.Y2019.M06.D03;

import com.clei.utils.PrintUtil;

public class FloatTest {
    public static void main(String[] args) {
        double d = 2.1474836E7;
        int i = (int) d;
        float f = 2.1474836E7f;
        PrintUtil.dateLine(i);

        PrintUtil.dateLine("FLOAT_MAX:" + Float.MAX_VALUE);
        PrintUtil.dateLine("FLOAT_MIN:" + Float.MIN_VALUE);
        PrintUtil.dateLine("INTEGER_MAX:" + Integer.MAX_VALUE);
        PrintUtil.dateLine("INTEGER_MIN:" + Integer.MIN_VALUE);
        PrintUtil.dateLine(Integer.MAX_VALUE / 100);
        PrintUtil.dateLine(Integer.MAX_VALUE / 100 == f);
    }
}
