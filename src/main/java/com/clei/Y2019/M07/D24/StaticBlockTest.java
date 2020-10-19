package com.clei.Y2019.M07.D24;

import com.clei.utils.PrintUtil;

/**
 * 验证下 一个类是否能存在多个静态代码块
 * 可以滴
 * 顺序呢
 * 从上而下
 */
public class StaticBlockTest {
    private static int i = 10;

    static {
        i += 5;
    }

    static{
        i /= 3;
    }

    public static void main(String[] args) {
        for (int j = 0; j < 20; j++) {
            PrintUtil.dateLine(i);
        }
    }
}
