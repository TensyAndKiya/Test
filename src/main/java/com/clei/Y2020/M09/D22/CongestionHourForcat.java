package com.clei.Y2020.M09.D22;

import java.math.BigDecimal;

/**
 * 小时拥堵预测
 */
public class CongestionHourForcat {

    private Long roadSectionId;

    private Long roadId;

    private Integer date;

    private Integer hour;

    private BigDecimal congestionIndex;

    private Integer congestionType;

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

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public BigDecimal getCongestionIndex() {
        return congestionIndex;
    }

    public void setCongestionIndex(BigDecimal congestionIndex) {
        this.congestionIndex = congestionIndex;
    }

    public Integer getCongestionType() {
        return congestionType;
    }

    public void setCongestionType(Integer congestionType) {
        this.congestionType = congestionType;
    }
}
