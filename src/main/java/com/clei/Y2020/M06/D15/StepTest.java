package com.clei.Y2020.M06.D15;

import com.clei.utils.PrintUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * n个台阶 每次只能走1步或者2步 有多少种方法
 */
public class StepTest {

    private static int num = 0;

    private static Map<Integer,Long> map;

    public static void main(String[] args) {

        int step = 200;

        // 这个太慢了 先注释掉
        // step(step);

        PrintUtil.log(num);

        Long temp = tempNewStep(step);

        PrintUtil.log(temp);

    }

    public static void step(int lastStep){
        step(lastStep,1);
    }

    public static void step(int lastStep, int minus){

        if(0 >= lastStep - minus ){
            num ++;
            return;
        }

        lastStep = lastStep - minus;

        step(lastStep, 1);
        step(lastStep, 2);
    }

    /**
     * 考虑到效率问题 有了newStep2
     * @param lastStep
     * @return
     */
    public static Long tempNewStep(int lastStep){
        if(lastStep > 1){
            map = new HashMap<>(4);
            return newStep2(lastStep);
        }

        return newStep1(lastStep);
    }

    /**
     * 斐波那契数列
     * @param lastStep
     * @return
     */
    public static long newStep1(int lastStep){
        if(lastStep < 4){
            return lastStep;
        }

        return newStep1(lastStep - 1) + newStep1(lastStep - 2);
    }

    /**
     * 斐波那契数列
     * @param lastStep
     * @return
     */
    public static Long newStep2(int lastStep){

        if(lastStep < 3){
            return Long.valueOf(lastStep);
        }

        Integer key1 = 1;
        Integer key2 = 2;

        // 全是在一个函数体内操作的，可以不用map
        // 要把f(1)到f(n)存下的话用map
        map.put(key1,2L);
        map.put(key2,3L);

        for (int i = 3; i < lastStep; i++) {

            Long step1 = map.get(key1);
            Long step2 = map.get(key2);

            Long l = step1 + step2;

            map.put(key1,step2);
            map.put(key2,l);
        }

        return map.get(key2);
    }

}
