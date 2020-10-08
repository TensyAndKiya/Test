package com.clei.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Pattern;

public class StringUtil {
    public static String createOrderNo(){
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "";
        String seed = "999999";
        String noStr = new Random().nextInt(Integer.parseInt(seed)) + "";
        StringBuffer result = new StringBuffer();
        result.append(timestamp);
        for(int i=0; i<(seed.length()-noStr.length()); i++){
            result.append("0");
        }
        result.append(noStr);

        return result.toString();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return null == str || 0 == str.length();
    }

    /**
     * str 是否包含 strArr中的某个字符串
     *
     * @param str
     * @param strArr
     * @return
     */
    public static boolean contains(String str, String[] strArr) {

        if (isEmpty(str) || null == strArr || 0 == strArr.length) {
            throw new RuntimeException("参数为空");
        }

        for (String s : strArr) {

            if (isNotEmpty(s)) {

                boolean result = str.contains(s);

                if (result) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 是数字
     *
     * @param str
     * @return
     */
    public static boolean isDigit(String str) {
        String pattern = "\\d*";

        return Pattern.matches(pattern, str);
    }

    /**
     * 是数字或字符
     * @param str
     * @return
     */
    public static boolean isDigitOrChar(String str){
        String pattern = "[0-9A-Za-z]*";

        return Pattern.matches(pattern,str);
    }

    public static String[] arrayRemoveDuplicate(){
        String[] array = new String[0];
        array = new HashSet<String>(Arrays.asList(array)).toArray(array);
        return array;
    }
}
