package com.clei.Y2021.M01.D05;

import com.clei.enums.Color;
import com.clei.enums.InstantNoodle;
import com.clei.utils.PrintUtil;

/**
 * 枚举使用练习
 *
 * @author KIyA
 */
public class EnumTest {

    public static void main(String[] args) {
        simple();
        complex();
    }

    /**
     * 简单的enum的使用
     */
    private static void simple() {
        // 所有值
        Color[] values = Color.values();
        for (Color value : values) {
            PrintUtil.log(value);
        }
        // false表示 values每次生成一个新数组
        PrintUtil.log(values == Color.values());
        // getByName 没有name会抛异常
        Color color1 = Color.valueOf("BLACK");
        // 直接指定color
        Color color2 = Color.BLACK;
        // 判断相等
        PrintUtil.log(color1.equals(color2));
        PrintUtil.log(color1 == color2);
        // 序号
        PrintUtil.log(color1.ordinal());
        // name
        PrintUtil.log(color1.name());
    }

    /**
     * 稍复杂的enum的使用
     */
    private static void complex() {
        InstantNoodle i1 = InstantNoodle.getByValue(1);
        switch (i1) {
            case KSF:
                PrintUtil.log("还行");
                break;
            case TY:
                PrintUtil.log("最大的敌人是康师傅");
                break;
            case QMG:
                PrintUtil.log("好久不见");
                break;
            default:
                throw new RuntimeException("啥情况？");
        }
        PrintUtil.log(i1);
        PrintUtil.log(i1.getValue());
        PrintUtil.log(i1.getBrand());
        PrintUtil.log(i1.ordinal());
        PrintUtil.log(i1.name());
    }
}
