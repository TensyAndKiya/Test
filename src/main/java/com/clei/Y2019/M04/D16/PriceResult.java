package com.clei.Y2019.M04.D16;

import com.clei.utils.DateUtil;

import java.time.LocalDateTime;

/**
 * 计算价格结果
 *
 * @author KIyA
 */
class PriceResult {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int hours;
    private boolean overNight;
    private float totalPrice;

    public PriceResult(LocalDateTime startDate, LocalDateTime endDate, int hours, boolean overNight, float totalPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.hours = hours;
        this.overNight = overNight;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Result{" +
                "startDate=" + DateUtil.format(startDate) +
                ", endDate=" + DateUtil.format(endDate) +
                ", hours=" + hours +
                ", overNight=" + overNight +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
