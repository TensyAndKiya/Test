package com.clei.Y2019.M07.D23;

import com.clei.utils.PrintUtil;

import java.util.Calendar;
import java.util.regex.Pattern;

public class RegTest {
    public static void main(String[] args) {
        String pattern = "\\d{4}\\-\\d{2}\\-\\d{2}( \\d{2}:\\d{2}:\\d{2})?";
        PrintUtil.dateLine(Pattern.matches(pattern, "1995-10-03 00:00:00"));
        PrintUtil.dateLine(Calendar.getInstance().get(Calendar.MONTH));
    }
}
