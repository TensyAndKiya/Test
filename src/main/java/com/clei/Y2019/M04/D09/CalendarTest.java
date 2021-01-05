package com.clei.Y2019.M04.D09;

import com.clei.utils.PrintUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日历类使用测试
 *
 * @author KIyA
 */
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
        PrintUtil.dateLine(cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH));

        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
            PrintUtil.dateLine(true);
        }
        PrintUtil.dateLine(false);

        PrintUtil.dateLine(sdf.format(cal1.getTime()));
        PrintUtil.dateLine(sdf.format(cal2.getTime()));
    }
}
