package com.clei.utils;

import java.math.BigDecimal;

/**
 * BigDecimal 工具类
 *
 * @author KIyA
 */
public class BigDecimalUtil {

    /**
     * 获得n位小数的float
     *
     * @param f float
     * @param n scale
     * @return float
     */
    public static float getScaleFloat(float f, int n) {
        BigDecimal bigDecimal = new BigDecimal(f).setScale(n, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.floatValue();
    }

    /**
     * 获得n位小数的float
     *
     * @param b BigDecimal
     * @param n scale
     * @return float
     */
    public static float getScaleFloat(BigDecimal b, int n) {
        return b.setScale(n, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 获得n位小数的float字符串
     *
     * @param f String float
     * @param n scale
     * @return String float
     */
    public static String getScaleFloat(String f, int n) {
        return Float.toString(getScaleFloat(Float.parseFloat(f), n));
    }

    /**
     * 去掉字符串小数尾巴上的0
     *
     * @param str String float
     * @return String float
     */
    public static String trimTailZeros(String str) {
        int dotIndex = str.indexOf(".");
        if (-1 != dotIndex) {
            int length = str.length();
            while (length != dotIndex) {
                if ('0' != str.charAt(--length)) {
                    break;
                }
            }
            if (length == str.length()) {
                return str;
            }
            if (length == dotIndex) {
                return str.substring(0, dotIndex);
            }
            return str.substring(0, length + 1);
        }
        return str;
    }
}
