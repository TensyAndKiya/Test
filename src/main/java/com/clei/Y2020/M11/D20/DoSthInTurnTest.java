package com.clei.Y2020.M11.D20;

import com.clei.utils.PrintUtil;
import com.clei.utils.SystemUtil;

import java.util.concurrent.locks.LockSupport;

/**
 * 轮流做事
 *
 * @author KIyA
 */
public class DoSthInTurnTest {

    public static void main(String[] args) {
        print(4, 5);
        SystemUtil.pause();
    }

    /**
     * n个线程依次print
     *
     * @param threadNum
     * @param loopTimes
     */
    private static void print(int threadNum, int loopTimes) {
        Thread[] arr = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            int index = i;
            Thread t = new Thread(() -> print(loopTimes, index, arr));
            arr[i] = t;
            t.start();
        }
        // 第一个开工
        LockSupport.unpark(arr[0]);
    }

    private static void print(int loopTimes, int index, Thread[] arr) {
        char c = (char) ('A' + index);
        index++;
        if (index == arr.length) {
            index = 0;
        }
        for (int i = 0; i < loopTimes; i++) {
            // 先等着
            LockSupport.park();
            // 要做的事
            PrintUtil.log(c);
            // 让下一个开工
            LockSupport.unpark(arr[index]);
        }
    }
}
