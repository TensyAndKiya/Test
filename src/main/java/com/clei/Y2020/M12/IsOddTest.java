package com.clei.Y2020.M12;

import com.clei.utils.PrintUtil;

/**
 * 判断一个整数是否是奇数
 *
 * @author KIyA
 */
public class IsOddTest {

    public static void main(String[] args) {
        PrintUtil.log(f1(1));
        PrintUtil.log(f1(-1));
        PrintUtil.log(f2(1));
        PrintUtil.log(f2(-1));
        PrintUtil.log(f3(1));
        PrintUtil.log(f3(-1));
    }

    /**
     * 方法1 无法判断负数
     *
     * @param num
     * @return
     */
    private static boolean f1(int num) {
        return 1 == num % 2;
    }

    /**
     * 方法2 效率还差点
     *
     * @param num
     * @return
     */
    private static boolean f2(int num) {
        return !(0 == num % 2);
    }

    /**
     * 方法3 完美
     *
     * @param num
     * @return
     */
    private static boolean f3(int num) {
        return 1 == (1 & num);
    }
}
