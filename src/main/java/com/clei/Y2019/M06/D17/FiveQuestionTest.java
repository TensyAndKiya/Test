package com.clei.Y2019.M06.D17;

import com.clei.utils.PrintUtil;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 面试遇到的几个小问题
 *
 * @author KIyA
 */
public class FiveQuestionTest {

    public static void main(String[] args) {
        PrintUtil.log(q1());
        PrintUtil.log(q2());
        //PrintUtil.log(q3());
        q4();
        q5();
    }

    //false
    //IEEE数字计算规范了解一哈？？？
    //java float类型浮点数共32位，其中1个符号位，23个有效数字位，8个指数位
    //小数中只有0.5，0.25，0.125，或它们的整倍数能够精确地表示,其它的只能近似表示
    private static boolean q1() {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        return a == b;
    }

    //false
    private static boolean q2() {
        Float a = Float.valueOf(1.0f - 0.9f);
        Float b = Float.valueOf(0.9f - 0.8f);
        return a.equals(b);
    }

    //会报Null
    private static String q3() {
        String result;
        String param = null;
        switch (param) {
            case "null":
                result = "null";
                break;
            default:
                result = "default";
        }
        return result;
    }

    //哪种赋值方式好
    //b好
    private static void q4() {
        BigDecimal a = new BigDecimal(0.1);
        PrintUtil.log(a);
        BigDecimal b = new BigDecimal("0.1");
        PrintUtil.log(b);
    }

    //并发锁问题
    private static void q5() {
        final Lock lock = new ReentrantLock();
        boolean lockSuccess = lock.tryLock();
        try {
            if (lockSuccess) {
                //需要加锁的代码
            }
        } catch (Exception e) {
            PrintUtil.log("ERROR!!!!", e);
        } finally {
            lock.unlock();
        }
    }
}
