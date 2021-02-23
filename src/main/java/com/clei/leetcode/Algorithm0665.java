package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 非递减数列
 * 给你一个长度为 n 的整数数组，请你判断在 最多 改变 1 个元素的情况下，该数组能否变成一个非递减数列。
 * <p>
 * 我们是这样定义一个非递减数列的： 对于数组中所有的 i (0 <= i <= n-2)，总满足 nums[i] <= nums[i + 1]。
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums = [4,2,3]
 * 输出: true
 * 解释: 你可以通过把第一个4变成1来使得它成为一个非递减数列。
 * 示例 2:
 * <p>
 * 输入: nums = [4,2,1]
 * 输出: false
 * 解释: 你不能在只改变一个元素的情况下将其变为非递减数列。
 * <p>
 * <p>
 * 说明：
 * <p>
 * 1 <= n <= 10 ^ 4
 * - 10 ^ 5 <= nums[i] <= 10 ^ 5
 *
 * @author KIyA
 */
public class Algorithm0665 {

    public static void main(String[] args) {
        PrintUtil.log(checkPossibility(new int[]{3, 4, 2, 3}));
    }

    private static boolean checkPossibility(int[] nums) {
        int length = nums.length;
        boolean changed = false;
        for (int i = 1; i < length; i++) {
            // 优先考虑将前面的数字变小
            if (nums[i - 1] > nums[i]) {
                if (changed) {
                    return false;
                } else {
                    // 前面有数字
                    if (i - 2 > -1) {
                        // 只能变后
                        if (nums[i - 2] > nums[i]) {
                            nums[i] = nums[i - 1];
                        } else {
                            // 变前
                            nums[i - 1] = nums[i];
                        }
                    } else {
                        // 变前
                        nums[i - 1] = nums[i];
                    }
                    changed = true;
                }
            }
        }
        return true;
    }
}
