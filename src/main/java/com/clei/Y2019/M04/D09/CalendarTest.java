package com.clei.Y2019.M04.D09;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//温江的哟wenjiang
public class CalendarTest {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        final String dateStr1 = "2018 08 08 12:12:12";
        final String dateStr2 = "1997 07 08 13:13:13";
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(sdf.parse(dateStr1));
        cal2.setTime(sdf.parse(dateStr2));

        //判断同一天
        System.out.println(cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) );

        if( cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) ){
            System.out.println(true);
        }
        System.out.println(false);

        System.out.println(sdf.format(cal1.getTime()));
        System.out.println(sdf.format(cal2.getTime()));
    }
}
