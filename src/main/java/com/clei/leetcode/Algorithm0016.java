package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.Arrays;

/**
 * 最接近的三数之和
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 * <p>
 * <p>
 * <p>
 * 示例：
 * <p>
 * 输入：nums = [-1,2,1,-4], target = 1
 * 输出：2
 * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
 *
 * @author KIyA
 */
public class Algorithm0016 {

    public static void main(String[] args) {
        int[] arr = {0, 0, 0};
        int target = 1;
        PrintUtil.log(threeSumClosest(arr, target));
    }

    private static int threeSumClosest(int[] nums, int target) {
        // 排序
        Arrays.sort(nums);
        // 双指针
        int length = nums.length;
        int result = target < 0 ? Integer.MAX_VALUE + target : Integer.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == target) {
                    return sum;
                }
                if (Math.abs(sum - target) < Math.abs(result - target)) {
                    result = sum;
                }
                if (sum > target) {
                    int tempRight = right - 1;
                    while (left < tempRight && nums[right] == nums[tempRight]) {
                        tempRight--;
                    }
                    right = tempRight;
                } else {
                    int tempLeft = left + 1;
                    while (tempLeft < right && nums[left] == nums[tempLeft]) {
                        tempLeft++;
                    }
                    left = tempLeft;
                }
            }
        }
        return result;
    }

}
