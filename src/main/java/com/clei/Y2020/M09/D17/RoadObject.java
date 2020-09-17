package com.clei.Y2020.M09.D17;

import java.math.BigDecimal;

/**
 * 道路信息
 *
 * @author KIyA
 * @date 2020-09-17
 */
public class RoadObject {

    /**
     * 道路主键id
     */
    private Long roadId;

    /**
     * 道路路段主键id
     */
    private Long roadSectionId;

    /**
     * 道路编号
     */
    private String roadCode;

    /**
     * 道路名
     */
    private String roadName;

    /**
     * 道路长度
     */
    private BigDecimal length;

    /**
     * 道路方向
     */
    private String direction;

    /**
     * 道路中心点经度
     */
    private BigDecimal centerLon;

    /**
     * 道路中心点纬度
     */
    private BigDecimal centerLat;

    /**
     * 道路经纬度数组
     */
    private String geo;

    @Override
    public String toString() {
        return "RoadObject{" +
                "roadCode='" + roadCode + '\'' +
                ", roadName='" + roadName + '\'' +
                ", length=" + length +
                ", direction='" + direction + '\'' +
                ", centerLon=" + centerLon +
                ", centerLat=" + centerLat +
                ", geo='" + geo + '\'' +
                '}';
    }

    public Long getRoadId() {
        return roadId;
    }

    public void setRoadId(Long roadId) {
        this.roadId = roadId;
    }

    public String getRoadCode() {
        return roadCode;
    }

    public void setRoadCode(String roadCode) {
        this.roadCode = roadCode;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
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

    public BigDecimal getCenterLon() {
        return centerLon;
    }

    public void setCenterLon(BigDecimal centerLon) {
        this.centerLon = centerLon;
    }

    public BigDecimal getCenterLat() {
        return centerLat;
    }

    public void setCenterLat(BigDecimal centerLat) {
        this.centerLat = centerLat;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }
}
