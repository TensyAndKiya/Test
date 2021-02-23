package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 回文数
 * 给你一个整数 x ，如果 x 是一个回文整数，返回 ture ；否则，返回 false 。
 * <p>
 * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。例如，121 是回文，而 123 不是。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：x = 121
 * 输出：true
 * 示例 2：
 * <p>
 * 输入：x = -121
 * 输出：false
 * 解释：从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3：
 * <p>
 * 输入：x = 10
 * 输出：false
 * 解释：从右向左读, 为 01 。因此它不是一个回文数。
 * 示例 4：
 * <p>
 * 输入：x = -101
 * 输出：false
 * <p>
 * <p>
 * 提示：
 * <p>
 * -231 <= x <= 231 - 1
 * <p>
 * <p>
 * 进阶：你能不将整数转为字符串来解决这个问题吗？
 *
 * @author KIyA
 */
public class Algorithm0009 {

    public static void main(String[] args) {
        int num = 121;
        PrintUtil.log(num);
        PrintUtil.log(isPalindrome(num));
    }

    private static boolean isPalindrome(int x) {
        // 越小几率的判断的越放到后面
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int reverseNum = 0;
        // 算一半前提是10的整倍数的数字已经排除了
        // 用大于而不是x == 0，只用计算一半的数字，而不用为了快速结束在循环体里加判断
        while (x > reverseNum) {
            reverseNum = reverseNum * 10 + x % 10;
            x = x / 10;
        }
        return x == reverseNum || x == reverseNum / 10;
    }
}
