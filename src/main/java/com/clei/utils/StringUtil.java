package com.clei.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str){
        return null == str || 0 == str.length();
    }
}
