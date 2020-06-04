package com.clei.Y2020.M06.D03;

/**
 * 位移运算符的操作测试
 *
 * @author KIyA
 * @date 2020-06-03 这个可以从包名看出来
 */
public class NumberTest {
    public static void main(String[] args) {
        System.out.println(16 >>> 2);
        System.out.println(16 >> 2);

        // >>> 是左边补0
        System.out.println(-16 >>> 2);
        // >> 是有符号的
        System.out.println(-16 >> 2);

        System.out.println(1 << 2);
        System.out.println(-1 << 2);

        // 为啥没有<<<呢，因为<<是右边补0，影响不了正负
    }
}
