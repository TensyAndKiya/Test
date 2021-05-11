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

        PrintUtil.log(~1);
        PrintUtil.log(true ^ false);
        PrintUtil.log(true & false);
        PrintUtil.log(true | false);

        PrintUtil.log(16 >>> 2);
        PrintUtil.log(16 >> 2);

        PrintUtil.log(-3 >> 1);
        PrintUtil.log(-3 / 2);
        PrintUtil.log(1 >> 2);
        PrintUtil.log(1 / 4);

        // >>> 是左边补0
        PrintUtil.log(Integer.toBinaryString(-16));
        PrintUtil.log(Integer.toBinaryString(-16 >>> 1));
        PrintUtil.log(Integer.toBinaryString(-16 >>> 2));
        PrintUtil.log(Integer.toBinaryString(-16 >> 1));
        PrintUtil.log(Integer.toBinaryString(-16 >> 2));
        PrintUtil.log(Integer.toBinaryString(Integer.MAX_VALUE));
        PrintUtil.log(-16 >>> 1);
        PrintUtil.log(-16 >>> 2);
        PrintUtil.log(Integer.MAX_VALUE);
        // >> 是有符号的
        PrintUtil.log(-16 >> 2);

        PrintUtil.log(1 << 2);
        PrintUtil.log(-1 << 2);

        // 为啥没有<<<呢，因为<<是右边补0，影响不了正负
        PrintUtil.log(NumUtil.toBinaryString(-1));
        PrintUtil.log(NumUtil.toBinaryString(-1).length());
    }
}