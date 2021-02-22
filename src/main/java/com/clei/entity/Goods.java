package com.clei.entity;

/**
 * 商品
 *
 * @author KIyA
 */
public class Goods {

    /**
     * 名称
     */
    private String name;

    /**
     * 重量 单位：千克
     */
    private float weight;

    /**
     * 价格 单位：元
     */
    private float price;

    public Goods(String name, float weight, float price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public Goods() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
