package com.clei.Y2020.M09.D22;

import com.clei.utils.PrintUtil;

/**
 * 将秒数转为 23:59:33字符串
 *
 * @author chenlei51
 */
public class IntSecond2StrTest {

    public static void main(String[] args) {

        PrintUtil.dateLine(convertSecond2Str((24 + 23) * 3600 + 59 * 60 + 59));

    }

    /**
     * 将秒数转为 23:59:33字符串
     *
     * @param congestionTime
     * @return
     */
    private static String convertSecond2Str(Integer congestionTime) {

        int hourSecond = 3600;
        int minuteSecond = 60;

        StringBuilder sb = new StringBuilder(8);

        // 时
        if (congestionTime > hourSecond - 1) {
            int hour = congestionTime / hourSecond;
            congestionTime %= hourSecond;

            if (hour < 10) {
                sb.append('0');
            }
            sb.append(hour);
            sb.append(':');
        } else {
            sb.append("00:");
        }

        // 分
        if (congestionTime > minuteSecond - 1) {
            int minute = congestionTime / minuteSecond;
            congestionTime %= minuteSecond;

            if (minute < 10) {
                sb.append('0');
            }
            sb.append(minute);
            sb.append(':');
        } else {
            sb.append("00:");
        }

        // 秒
        if (congestionTime < 10) {
            sb.append('0');
        }
        sb.append(congestionTime);

        return sb.toString();
    }

}
