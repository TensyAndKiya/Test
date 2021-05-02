package com.clei.entity;

import java.math.BigDecimal;

/**
 * 路段运行状态源对象
 */
public class SectionRunState {

    private String uuid;
    private Long roadSectionId;
    private BigDecimal congestionIndex;
    private BigDecimal speed;
    private String dateTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getCongestionIndex() {
        return congestionIndex;
    }

    public Long getRoadSectionId() {
        return roadSectionId;
    }

    public void setRoadSectionId(Long roadSectionId) {
        this.roadSectionId = roadSectionId;
    }

    public void setCongestionIndex(BigDecimal congestionIndex) {
        this.congestionIndex = congestionIndex;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
