package com.clei.Y2019.M04.D16;

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
    private final static int FREE_MINUTES = 20;

    /**
     * 每天多少元
     */
    private final static float PRICE_PER_DAY = 18f;

    public static void main(String[] args) {
        LocalDateTime startDate = LocalDateTime.now();
        for (int i = 0; i < 24; i++) {
            LocalDateTime endDate = startDate.plusHours((i + 1) * 2L);
            PrintUtil.log(myCalc(startDate, endDate));
        }
    }

    /**
     * 详细计费规则请看 resources/images/ChargingRule2图。。
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private static PriceResult myCalc(LocalDateTime startDate, LocalDateTime endDate) {
        if (null == startDate || null == endDate) {
            return new PriceResult(startDate, endDate, 0, false, 0);
        }
        // 免费时间内
        if (ChronoUnit.MINUTES.between(startDate, endDate) <= FREE_MINUTES) {
            return new PriceResult(startDate, endDate, 0, false, 0);
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
        //过夜时间0点到6点
        float price = 0f;
        boolean overNight = false;
        if (DateUtil.isSameDay(tempStartDate, endDate)) {
            //同一天的话开始时间小于6点。。且开始不等于结束的话。。跨夜
            LocalDateTime tempDate = LocalDateTime.of(tempStartDate.toLocalDate(), LocalTime.of(6, 0, 0));
            if (tempStartDate.isBefore(tempDate) && tempStartDate.isBefore(endDate)) {
                overNight = true;
                price += 3;
            }
        } else {
            //非同一天的话结束时间大于0点。。且开始不等于结束的话。。跨夜
            LocalDateTime tempDate = LocalDateTime.of(tempStartDate.toLocalDate(), LocalTime.of(0, 0, 0));
            if (tempDate.isBefore(endDate) && tempStartDate.isBefore(endDate)) {
                overNight = true;
                price += 3;
            }
        }
        int hours = getDiffHours(tempStartDate, endDate);
        //价格计算
        if (hours > 0) {
            if (hours < 2) {
                price += 3;
            } else if (hours < 4) {
                price += 5;
            } else {
                price += (5 + (hours - 3));
            }
            //每日限制为18
            price = Math.min(price, PRICE_PER_DAY);
            totalPrice += price;
        }
        return new PriceResult(startDate, endDate, getDiffHours(startDate, endDate), overNight, totalPrice);
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
        return (int) (Math.ceil((ChronoUnit.MILLIS.between(startDate, endDate) - 0.0) / HOUR_MILLS));
    }
}


