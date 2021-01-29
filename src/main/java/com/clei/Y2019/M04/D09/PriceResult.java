package com.clei.Y2019.M04.D09;

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
    private int dayHours;
    private int nightHours;
    private float dayPrice;
    private float nightPrice;
    private float totalPrice;

    public PriceResult(LocalDateTime startDate, LocalDateTime endDate, int dayHours, int nightHours, float dayPrice, float nightPrice, float totalPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.dayHours = dayHours;
        this.nightHours = nightHours;
        this.dayPrice = dayPrice;
        this.nightPrice = nightPrice;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Result{" +
                "startDate=" + DateUtil.format(startDate) +
                ", endDate=" + DateUtil.format(endDate) +
                ", dayHours=" + dayHours +
                ", nightHours=" + nightHours +
                ", dayPrice=" + dayPrice +
                ", nightPrice=" + nightPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
