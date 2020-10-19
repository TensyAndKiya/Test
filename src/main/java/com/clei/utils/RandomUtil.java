package com.clei.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 随机数相关工具类
 */
public class RandomUtil {
    // 想法1
    // 1 从1到1000里随机选取100个

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();

        int[] array = intArray(20000);
        int[] randomArray = randomArray(array, 10000);

        long end = System.currentTimeMillis();
        PrintUtil.println("耗时 ： {}ms", end - begin);

        PrintUtil.dateLine("origin : " + Arrays.toString(array));
        PrintUtil.dateLine("result : " + Arrays.toString(randomArray));

        PrintUtil.dateLine("size : " + randomArray.length);
    }

    // 从 0 到 n 里 选 num 个
    public static int[] randomArray(int[] array,int num){
        int length = array.length;

        // 1000里选 900个 == 1000里选 100个
        boolean other = false;
        int[] originArray = array;
        if(num > length / 2){
            other = true;
            num = length - num;
        }
        // 装选入的数字
        Set<Integer> set = new HashSet<>(num);

        // 筛选出的 set
        outer:
        while (set.size() < num){
            // 还需要多少个数字
            int tempNum = num - set.size();
            // 长度为n倍tempNum 的 数组(n > 1)
            // 下面两个都是应对一次生成没有满足的状况
            // 使用tempNum而不是num保证 需要的数字越来越少的时候 生成的数组越来越小 浪费少
            // 使用array.length 而不是 length 保证 不会出现IndexOutOfBoundsException
            int[] randomIntArray = randomIntArray(tempNum * 3 / 2 ,0,array.length);

            for(int i : randomIntArray){
                set.add(array[i]);

                if(num == set.size()){
                    break outer;
                }
            }
            array = getLastArray(array,set);
        }

        //
        if(other){
            return getLastArray(originArray,set);
        }

        return set.stream().mapToInt(i -> i).toArray();
    }

    /**
     * array 里 的值 不在 collection 里 的 凑一个array
     * @param array
     * @param collection
     * @return
     */
    private static int[] getLastArray(int[] array, Collection<Integer> collection) {
        int[] newArray = new int[array.length - collection.size()];

        int j = 0;
        for (int i : array){
            if(!collection.contains(i)){
                newArray[j] = i;
                j ++;
            }
        }

        return newArray;
    }

    /**
     * 获得一个长度为num 大小在origin 到 bound[左闭右开]之间的int数组
     * @param num
     * @param origin
     * @param bound
     * @return
     */
    public static int[] randomIntArray(int num,int origin,int bound){
        Random random = new Random();
        int[] array = random.ints(num,origin,bound).toArray();
        return array;
    }

    /**
     * 获得一个长度为i 的 从0到i[左闭右开] 的 int数组
     * @param i
     * @return
     */
    public static int[] intArray(int i){
        if(i < 0){
            return null;
        }
        int[] array = new int[i];

        for (int j = 0; j < i; j++) {
            array[j] = j;
        }
        return array;
    }

    /**
     * 获得一个长度为i 的 从0到i[左闭右开] 的 Integer 列表
     * @param i
     * @return
     */
    public static List<Integer> integerList(int i){
        if(i < 1){
            return null;
        }

        List<Integer> list = new ArrayList<>(i);

        for (int j = 0; j < i; j++) {
            list.add(j);
        }
        return list;
    }
}
