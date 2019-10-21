package com.clei.Y2019.M05.D15;

import java.util.Calendar;

public class DateFormatTest {

    private static final int ONE_HOUR_MILLS = 60 * 60 * 1000;
    private static final int ONE_DAY_MILLS = 24 * ONE_HOUR_MILLS;

    public static void main(String[] args) throws Exception{
        /*SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");*/
        String dateStr1 = "07:00:00";
        String dateStr2 = "22:00:00";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,7);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        System.out.println(isBetween(cal,dateStr1,dateStr2));
    }

    private static int getSecond(String str){
        int second = 0;
        if( null != str && !"".equals(str) && !":00".equals(str)){
            String[] arr = str.split(":");
            if(arr.length == 3){
                second = Integer.parseInt(arr[0]) * 3600;
                second += Integer.parseInt(arr[1]) * 60;
                second += Integer.parseInt(arr[2]);
            }
        }
        return second;
    }

    public static boolean isBetween(Calendar cal, String dayStartTime, String dayEndTime){
        //因为是从1970 01 01 00:00:00 (而我们这里是+8时区)开始的 。。开始时刻为0
        //所以加上8小时的毫秒数再除余一天毫秒数
        //最后得到的便是今天的毫秒数
        long todaySecond = ((cal.getTimeInMillis() + 8 * ONE_HOUR_MILLS)%ONE_DAY_MILLS)/1000;
        int startSecond = getSecond(dayStartTime);
        int endSecond = getSecond(dayEndTime);
        System.out.println(todaySecond);
        System.out.println(startSecond);
        System.out.println(endSecond);
        if(endSecond > startSecond && todaySecond >= startSecond && todaySecond < endSecond){
            return true;
        }
        return false;
    }
}
