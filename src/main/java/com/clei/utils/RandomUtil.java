package com.clei.utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 随机数相关工具类
 */
public class RandomUtil {
    // 想法1
    // 1 从1到1000里随机选取100个

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();

        int[] array = intArray(100000);
        int[] randomArray = randomArray(array,100);

        long end = System.currentTimeMillis();
        PrintUtil.println("耗时 ： {}ms",end - begin);

        System.out.println("origin : " + Arrays.toString(array));
        System.out.println("result : " + Arrays.toString(randomArray));

        System.out.println("size : " + randomArray.length);
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
        Set<Integer> set = new HashSet<>(num,1);

        // 筛选出的 set
        outer:
        while (set.size() < num){
            //
            int[] randomIntArray = randomIntArray(num * 3 / 2,0,length);

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
     * array 里 的值 不在 set 里 的 凑一个array
     * @param array
     * @param set
     * @return
     */
    private static int[] getLastArray(int[] array, Set<Integer> set) {
        int[] newArray = new int[array.length - set.size()];

        int j = 0;
        for (int i : array){
            if(!set.contains(i)){
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
        if(i < 1){
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
        List<Integer> list = Arrays.stream(intArray(i)).boxed().collect(Collectors.toList());
        return list;
    }
}
