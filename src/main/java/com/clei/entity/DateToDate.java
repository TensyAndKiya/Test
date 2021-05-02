package com.clei.entity;

/**
 * 一个日期到另一个日期
 */
public class DateToDate {

    private Integer source;

    private Integer target;

    public DateToDate(Integer source, Integer target) {
        this.source = source;
        this.target = target;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }
}
