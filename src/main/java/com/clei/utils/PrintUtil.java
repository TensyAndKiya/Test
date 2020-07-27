package com.clei.utils;

/**
 * 方便自己的输出打印
 */
public class PrintUtil {

    private final static String PLACE_STR = "{}";
    private final static int PLACE_LEN = PLACE_STR.length();

    public static void println(){
        System.out.println();
    }

    public static void println(Object obj){
        System.out.println(obj);
    }

    public static void println(final String str, Object... args){
        System.out.println(formatStr(str,args));
    }
    public static void print(final String str, Object... args){
        System.out.print(formatStr(str,args));
    }
    public static void print(Object obj){
        System.out.print(obj);
    }
    private static String formatStr(String str, Object... args){
        if(null == args || args.length == 0){
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        for(Object arg : args){
            int position = sb.indexOf(PLACE_STR);
            if(position > -1){
                sb.replace(position,position + PLACE_LEN,String.valueOf(arg));
            }else{
                break;
            }
        }
        return sb.toString();
    }
}
