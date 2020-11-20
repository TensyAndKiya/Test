package com.clei.Y2020.M10.D27;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;
import com.clei.utils.SystemUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据两个日期字符串，输出从开始到结束日期间的所有日期
 *
 * @author KIyA
 */
public class EpochDayTest {

    public static void main(String[] args) {

        String startDateStr = "2020-10-01";
        String endDateStr = "2020-10-27";

        String pattern = "yyyy-MM-dd";

        List<String> list = new ArrayList<>();
        LocalDate startDate = DateUtil.parse(startDateStr, pattern, LocalDate::from);
        LocalDate endDate = DateUtil.parse(endDateStr, pattern, LocalDate::from);

        long start = startDate.toEpochDay();
        long end = endDate.toEpochDay();

        while (start <= end) {
            startDateStr = DateUtil.format(startDate, pattern);
            list.add(startDateStr);
            start++;
            startDate = startDate.plusDays(1);
        }

        list.forEach(PrintUtil::println);

        SystemUtil.pause("");
    }

}
