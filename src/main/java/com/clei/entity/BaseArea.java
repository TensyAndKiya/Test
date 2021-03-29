package com.clei.entity;

public class BaseArea {

    private Long id;

    private String areaCode;

    private String areaName;

    private String parentCode;

    public BaseArea() {
    }

    public BaseArea(Long id, String areaCode, String areaName, String parentCode) {
        this.id = id;
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.parentCode = parentCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
