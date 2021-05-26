package com.clei.Y2019.M04.D09;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * 停车费计算
 *
 * @author KIyA
 */
public class CalcPriceTest {

    private final static int HOUR_MILLS = 3600000;

    /**
     * 入场n分钟内免费
     */
    private final static int FREE_MINUTES = 15;

    /**
     * 每天多少元
     */
    private final static float PRICE_PER_DAY = 31f;

    public static void main(String[] args) {
        LocalDateTime startDate = LocalDateTime.now();
        for (int i = 0; i < 24; i++) {
            LocalDateTime endDate = startDate.plusHours((i + 1) * 2L);
            PrintUtil.log(myCalc(startDate, endDate));
        }
    }


    /**
     * 详细计费规则请看 resources/images/ChargingRule图。。
     * 目前只实现了 15分钟内免费，和黑白两阶段的计费
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private static PriceResult myCalc(LocalDateTime startDate, LocalDateTime endDate) {
        if (null == startDate || null == endDate) {
            return new PriceResult(startDate, endDate, 0, 0, 0, 0, 0);
        }
        // 免费时间内
        if (ChronoUnit.MINUTES.between(startDate, endDate) <= FREE_MINUTES) {
            return new PriceResult(startDate, endDate, 0, 0, 0, 0, 0);
        }
        float totalPrice = 0f;
        // 开始时间临时变量用于计算 保持原开始时间不变
        LocalDateTime tempStartDate = LocalDateTime.from(startDate);
        // 若不是同一天的话可能超过24小时，则计算整天数的钱，并给开始时间重新赋值
        if (!DateUtil.isSameDay(tempStartDate, endDate)) {
            long diffDays = ChronoUnit.DAYS.between(tempStartDate, endDate);
            totalPrice += diffDays * PRICE_PER_DAY;
            tempStartDate = tempStartDate.plusDays(diffDays);
        }
        //计算24小时内的 白日与黑夜停车费
        int startHour = tempStartDate.getHour();
        int endHour = endDate.getHour();

        int dayHours = 0;
        int nightHours = 0;
        //开始时间在白日阶段
        if (startHour >= 8 && startHour < 20) {
            //if从白到白 else从白到黑
            if (endHour >= 8 && endHour < 20) {
                if (ChronoUnit.HOURS.between(tempStartDate, endDate) < 12) {
                    //同一天的白
                    dayHours = getDiffHours(tempStartDate, endDate);
                } else {
                    //中间过了一个完整夜阶段
                    dayHours = getDiffHours(tempStartDate, endDate) - 12;
                    nightHours = 12;
                }
            } else {
                //开始时间那一天的20点分割时刻
                LocalDateTime tempDate = LocalDateTime.of(tempStartDate.toLocalDate(), LocalTime.of(20, 0, 0));
                dayHours = getDiffHours(tempStartDate, tempDate);
                nightHours = getDiffHours(tempDate, endDate);
            }
        } else {
            //if从黑到黑，else从黑到白
            if (endHour < 8 || endHour >= 20) {
                if (ChronoUnit.HOURS.between(tempStartDate, endDate) < 12) {
                    //同一天的黑
                    nightHours = getDiffHours(tempStartDate, endDate);
                } else {
                    nightHours = getDiffHours(tempStartDate, endDate) - 12;
                    dayHours = 12;
                }
            } else {
                //结束时间那一天的8点分割时刻
                LocalDateTime tempDate = LocalDateTime.of(endDate.toLocalDate(), LocalTime.of(8, 0, 0));
                nightHours = getDiffHours(tempStartDate, tempDate);
                dayHours = getDiffHours(tempDate, endDate);
            }
        }

        float nightPrice = (float) (Math.ceil(nightHours / 4.0) * 2);

        float dayPrice;
        if (dayHours < 2) {
            dayPrice = 2 * dayHours;
        } else if (dayHours < 5) {
            dayPrice = 2 + (dayHours - 1);
        } else if (dayHours < 9) {
            dayPrice = 2 + 3 + 2 * (dayHours - 4);
        } else {
            dayPrice = 2 + 3 + 8 + 3 * (dayHours - 8);
        }
        totalPrice = totalPrice + dayPrice + nightPrice;

        return new PriceResult(startDate, endDate, dayHours, nightHours, dayPrice, nightPrice, totalPrice);
    }

    /**
     * 两个日期的小时差
     * 不足1小时按1小时
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private static int getDiffHours(LocalDateTime startDate, LocalDateTime endDate) {
        long millis = ChronoUnit.MILLIS.between(startDate, endDate);
        long hours = millis / HOUR_MILLS;
        long remainder = millis % HOUR_MILLS;
        if (remainder > 0) {
            hours++;
        }
        return (int) hours;
    }
}


