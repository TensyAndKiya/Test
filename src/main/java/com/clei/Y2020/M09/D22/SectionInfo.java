package com.clei.Y2020.M09.D22;

import java.util.List;

/**
 * 临时对象
 */
public class SectionInfo {

    private Long roadSectionId;

    private String roadSectionName;

    private String uuid;

    private String geo;

    private String direction;

    private List<String> geoList;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public List<String> getGeoList() {
        return geoList;
    }

    public void setGeoList(List<String> geoList) {
        this.geoList = geoList;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
