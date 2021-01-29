package com.clei.Y2019.M06.D03;

import com.clei.utils.PrintUtil;

public class FloatTest {
    public static void main(String[] args) {
        double d = 2.1474836E7;
        int i = (int) d;
        float f = 2.1474836E7f;
        PrintUtil.log(i);

        PrintUtil.log("FLOAT_MAX:" + Float.MAX_VALUE);
        PrintUtil.log("FLOAT_MIN:" + Float.MIN_VALUE);
        PrintUtil.log("INTEGER_MAX:" + Integer.MAX_VALUE);
        PrintUtil.log("INTEGER_MIN:" + Integer.MIN_VALUE);
        PrintUtil.log(Integer.MAX_VALUE / 100);
        PrintUtil.log(Integer.MAX_VALUE / 100 == f);
    }
}
