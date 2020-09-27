package com.clei.Y2020.M09.D22;

import java.math.BigDecimal;

/**
 * 路段点位置
 */
public class SectionPoint {

    private Long roadSectionId;
    private BigDecimal lon;
    private BigDecimal lat;

    public Long getRoadSectionId() {
        return roadSectionId;
    }

    public void setRoadSectionId(Long roadSectionId) {
        this.roadSectionId = roadSectionId;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }
}
