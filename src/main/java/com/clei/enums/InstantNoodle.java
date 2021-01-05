package com.clei.enums;

/**
 * 方便面 enum
 *
 * @author KIyA
 */
public enum InstantNoodle {

    KSF(1, "康师傅"),

    TY(2, "统一"),

    QMG(3, "巧面馆");

    /**
     * 数字序号
     */
    private final int value;

    /**
     * 品牌
     */
    private final String brand;

    /**
     * 私有构造函数
     *
     * @param value
     * @param brand
     */
    InstantNoodle(int value, String brand) {
        this.value = value;
        this.brand = brand;
    }

    /**
     * 根据value获取enum
     *
     * @param value
     * @return
     */
    public static InstantNoodle getByValue(int value) {
        InstantNoodle[] values = InstantNoodle.values();
        for (InstantNoodle i : values) {
            if (value == i.value) {
                return i;
            }
        }
        throw new RuntimeException("enum InstantNoodle 无对应value值");
    }

    /**
     * 根据value获取enum
     *
     * @param brand
     * @return
     */
    public static InstantNoodle getByBrand(String brand) {
        InstantNoodle[] values = InstantNoodle.values();
        for (InstantNoodle i : values) {
            if (i.brand.equals(brand)) {
                return i;
            }
        }
        throw new RuntimeException("enum InstantNoodle 无对应brand值");
    }

    public int getValue() {
        return value;
    }

    public String getBrand() {
        return brand;
    }
}
