package com.clei.Y2019.M07.D23;

import com.clei.utils.PrintUtil;

import java.util.regex.Pattern;

/**
 * 正则测试
 *
 * @author KIyA
 */
public class RegTest {

    public static void main(String[] args) {
        String pattern = "\\d{4}\\-\\d{2}\\-\\d{2}( \\d{2}:\\d{2}:\\d{2})?";
        PrintUtil.log(Pattern.matches(pattern, "1995-10-03 00:00:00"));
    }
}
