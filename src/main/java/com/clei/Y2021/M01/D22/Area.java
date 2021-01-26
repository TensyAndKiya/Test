package com.clei.Y2021.M01.D22;

/**
 * 区域
 *
 * @author KIyA
 */
public class Area {

    /**
     * 名称
     */
    private String areaName;

    /**
     * 编码
     */
    private String areaCode;

    /**
     * 级别 0为顶级
     */
    private int level;

    /**
     * 父级编码 顶级区域的父级编码为空
     */
    private String parentCode;

    public Area(String areaName, String areaCode, int level, String parentCode) {
        this.areaName = areaName;
        this.areaCode = areaCode;
        this.level = level;
        this.parentCode = parentCode;
    }
}
