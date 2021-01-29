package com.clei.Y2019.M05.D07;

import com.clei.utils.PrintUtil;

import java.math.BigDecimal;

public class BigDecimalTest {
    public static void main(String[] args) {
        float a = 12.00f;
        float b = 0.05f;
        float c = new BigDecimal((a * b) / (1 + a)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        PrintUtil.log((a * b) / (1 + a));
        PrintUtil.log(c);
    }
}
