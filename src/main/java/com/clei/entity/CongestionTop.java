package com.clei.entity;

import java.math.BigDecimal;

/**
 * 道路拥堵路段排名 entity
 *
 * @author KIyA51
 * @date 2020-09-09
 */
public class CongestionTop {

    /**
     * 道路id
     */
    private Long roadId;

    /**
     * 道路名称
     */
    private String roadName;

    /**
     * 道路路段id
     */
    private Long roadSectionId;

    /**
     * 道路路段名称
     */
    private String roadSectionName;

    /**
     * 排名
     */
    private Integer rank;

    /**
     * 拥堵指数
     */
    private BigDecimal congestionIndex;

    /**
     * 运行速度
     */
    private BigDecimal speed;

    private String startLocation;
    private String endLocation;
    private Integer congestionType;
    private BigDecimal length;
    private String direction;

    /**
     * 拥堵路段说明
     */
    private String congestionDescription;

    public Long getRoadId() {
        return roadId;
    }

    public void setRoadId(Long roadId) {
        this.roadId = roadId;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public Long getRoadSectionId() {
        return roadSectionId;
    }

    public void setRoadSectionId(Long roadSectionId) {
        this.roadSectionId = roadSectionId;
    }

    public String getRoadSectionName() {
        return roadSectionName;
    }

    public void setRoadSectionName(String roadSectionName) {
        this.roadSectionName = roadSectionName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public BigDecimal getCongestionIndex() {
        return congestionIndex;
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

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public Integer getCongestionType() {
        return congestionType;
    }

    public void setCongestionType(Integer congestionType) {
        this.congestionType = congestionType;
    }

    public String getCongestionDescription() {
        return congestionDescription;
    }

    public void setCongestionDescription(String congestionDescription) {
        this.congestionDescription = congestionDescription;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
