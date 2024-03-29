package com.clei.Y2019.M04.D02;

import com.clei.utils.PrintUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 四舍五入的几种方法
 *
 * @author KIyA
 */
public class MathRoundTest {

    public static void main(String[] args) {
        double d = 123.1455858123D;
        //method 1
        BigDecimal bd = new BigDecimal(String.valueOf(d));
        PrintUtil.log(bd);
        PrintUtil.log(bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        //method 2
        DecimalFormat df = new DecimalFormat("#.00");
        PrintUtil.log(df.format(bd.doubleValue()));
        //method 3
        PrintUtil.log(Math.round(d * 100) / 100f);
    }
}
