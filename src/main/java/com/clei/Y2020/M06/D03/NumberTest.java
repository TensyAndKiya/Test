package com.clei.Y2020.M06.D03;

import com.clei.utils.NumUtil;
import com.clei.utils.PrintUtil;

/**
 * 位移运算符的操作测试
 *
 * @author KIyA
 * @date 2020-06-03 这个可以从包名看出来
 */
public class NumberTest {
    public static void main(String[] args) {

        PrintUtil.println(16 >>> 2);
        PrintUtil.println(16 >> 2);

        System.out.println(Integer.toBinaryString(-1));

        // >>> 是左边补0
        PrintUtil.println(Integer.toBinaryString(-16));
        PrintUtil.println(Integer.toBinaryString(-16 >>> 1));
        PrintUtil.println(Integer.toBinaryString(-16 >>> 2));
        PrintUtil.println(Integer.toBinaryString(-16 >> 1));
        PrintUtil.println(Integer.toBinaryString(-16 >> 2));
        PrintUtil.println(Integer.toBinaryString(Integer.MAX_VALUE));
        PrintUtil.println(-16 >>> 1);
        PrintUtil.println(-16 >>> 2);
        PrintUtil.println(Integer.MAX_VALUE);
        // >> 是有符号的
        PrintUtil.println(-16 >> 2);

        PrintUtil.println(1 << 2);
        PrintUtil.println(-1 << 2);

        // 为啥没有<<<呢，因为<<是右边补0，影响不了正负
        System.out.println(NumUtil.toBinaryString(-1));
        System.out.println(NumUtil.toBinaryString(-1).length());
    }
}