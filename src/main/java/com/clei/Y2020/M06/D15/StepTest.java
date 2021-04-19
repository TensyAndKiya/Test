package com.clei.Y2020.M06.D15;

import com.clei.algorithm.other.FibonacciSequence;
import com.clei.utils.PrintUtil;

/**
 * n个台阶 每次只能走1步或者2步 有多少种方法
 *
 * @author KIyA
 */
public class StepTest {

    private static long num = 0;

    private final static int STEP = 50;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        // 这个太慢了
        step(STEP);
        long end = System.currentTimeMillis();
        PrintUtil.log("方法1耗时： {}ms", end - start);

        PrintUtil.log(num);

        start = System.currentTimeMillis();
        long temp = step2(STEP);
        end = System.currentTimeMillis();
        PrintUtil.log("方法2耗时： {}ms", end - start);

        PrintUtil.log(temp);
    }

    private static void step(int lastStep) {
        step(lastStep, 1);
        step(lastStep, 2);
    }

    private static void step(int lastStep, int minus) {
        lastStep -= minus;
        if (lastStep > 0) {
            step(lastStep, 1);
            step(lastStep, 2);
        } else if (lastStep == 0) {
            // 每个选择都能正确走到底才++
            num++;
        }
    }

    /**
     * 考虑到效率问题 有了step2
     * 斐波那契数列
     *
     * @param lastStep 剩余台阶数
     * @return
     */
    public static long step2(int lastStep) {

        return FibonacciSequence.f(lastStep + 1);

        // 全是在一个函数体内操作的，可以不用map
        // 要把f(1)到f(n)存下的话用map
        /*Map<Integer, Long> map = new HashMap<>(4);
        Integer key1 = 1;
        Integer key2 = 2;
        map.put(key1, 2L);
        map.put(key2, 3L);

        for (int i = 3; i < lastStep; i++) {

            Long step1 = map.get(key1);
            Long step2 = map.get(key2);

            Long l = step1 + step2;

            map.put(key1, step2);
            map.put(key2, l);
        }

        return map.get(key2);*/
    }
}
