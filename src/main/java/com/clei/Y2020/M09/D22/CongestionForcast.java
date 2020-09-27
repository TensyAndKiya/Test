package com.clei.Y2020.M09.D22;

import java.math.BigDecimal;

/**
 * 一天拥堵预测
 */
public class CongestionForcast {

    private Long roadSectionId;

    private Long roadId;

    private Integer date;

    private BigDecimal[] hourCongestionIndexSum = new BigDecimal[24];

    private int[] hourRecordCount = new int[24];

    public Long getRoadSectionId() {
        return roadSectionId;
    }

    public void setRoadSectionId(Long roadSectionId) {
        this.roadSectionId = roadSectionId;
    }

    public Long getRoadId() {
        return roadId;
    }

    public void setRoadId(Long roadId) {
        this.roadId = roadId;
    }

    public BigDecimal[] getHourCongestionIndexSum() {
        return hourCongestionIndexSum;
    }

    public void setHourCongestionIndexSum(BigDecimal[] hourCongestionIndexSum) {
        this.hourCongestionIndexSum = hourCongestionIndexSum;
    }

    public int[] getHourRecordCount() {
        return hourRecordCount;
    }

    public void setHourRecordCount(int[] hourRecordCount) {
        this.hourRecordCount = hourRecordCount;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }
}
