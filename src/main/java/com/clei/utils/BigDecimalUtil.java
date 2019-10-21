package com.clei.utils;

import java.math.BigDecimal;

public class BigDecimalUtil {
    // 获得n位小数的float
    public static float getScaleFloat(float f,int n){
        BigDecimal bigDecimal = new BigDecimal(f).setScale(n,BigDecimal.ROUND_HALF_UP);
        return bigDecimal.floatValue();
    }

    public static String getScaleFloat(String f,int n){
        return Float.toString(getScaleFloat(Float.parseFloat(f),n));
    }
}
