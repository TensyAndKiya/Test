package com.clei.utils;

import java.util.Arrays;
import java.util.Random;

/**
 * 随机数相关工具类
 */
public class RandomUtil {
    // 想法1
    // 1 从1000个数字里随机选取100个

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(Arrays.toString(intArray()));
    }

    public static int[] intArray(){
        Random random = new Random();
        // 左闭右开
        return random.ints(1000,1,2).toArray();
    }
}
