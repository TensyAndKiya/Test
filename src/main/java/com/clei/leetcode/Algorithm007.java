package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 整数反转
 * 给你一个 32 位的有符号整数 x ，返回 x 中每位上的数字反转后的结果。
 * <p>
 * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
 * <p>
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：x = 123
 * 输出：321
 * 示例 2：
 * <p>
 * 输入：x = -123
 * 输出：-321
 * 示例 3：
 * <p>
 * 输入：x = 120
 * 输出：21
 * 示例 4：
 * <p>
 * 输入：x = 0
 * 输出：0
 * <p>
 * <p>
 * 提示：
 * <p>
 * -231 <= x <= 231 - 1
 *
 * @author KIyA
 */
public class Algorithm007 {

    public static void main(String[] args) {
        int num = -132;
        PrintUtil.log(num);
        PrintUtil.log(reverse(num));
    }

    public static int reverse(int x) {
        int reverseNum = 0;
        while (x != 0) {
            int temp = x % 10;
            int temp2 = reverseNum * 10 + temp;
            // 逆运算相等才表示没有整数溢出
            if (reverseNum != (temp2 - temp) / 10) {
                return 0;
            }
            reverseNum = temp2;
            x = x / 10;
        }
        return reverseNum;
    }
}
