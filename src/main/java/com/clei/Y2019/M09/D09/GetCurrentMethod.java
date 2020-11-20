package com.clei.Y2019.M09.D09;

import com.clei.utils.PrintUtil;

public class GetCurrentMethod {
    public static void main(String[] args) {

        int i = 568962;
        int minutes = i / 60;
        int hours = minutes / 60;
        int days = hours / 24;
        PrintUtil.println("days:{},hours:{},minutes:{},seconds:{}",days,hours,minutes,i);

        theMethod(1,"2");
    }

    private static boolean theMethod(int a,String b){
        StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        String method = element.getMethodName();
        PrintUtil.dateLine(method);
        return false;
    }

}
