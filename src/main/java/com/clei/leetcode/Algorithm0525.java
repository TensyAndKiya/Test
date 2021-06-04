package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 连续数组
 * 给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums = [0,1]
 * 输出: 2
 * 说明: [0, 1] 是具有相同数量0和1的最长连续子数组。
 * 示例 2:
 * <p>
 * 输入: nums = [0,1,0]
 * 输出: 2
 * 说明: [0, 1] (或 [1, 0]) 是具有相同数量0和1的最长连续子数组。
 *
 * @author KIyA
 */
public class Algorithm0525 {

    public static void main(String[] args) {
        PrintUtil.log(findMaxLength(new int[]{0, 0, 1}));
        PrintUtil.log(findMaxLength2(new int[]{0, 0, 1}));
    }

    /**
     * 解
     * 思路：将0转为-1，前缀和，数组代替HashMap
     *
     * @param nums 连续数组
     * @return 解
     */
    private static int findMaxLength(int[] nums) {
        int max = 0;
        int n = nums.length;
        int[] hash = new int[2 * n + 1];

        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (0 == nums[i]) {
                sum--;
            } else {
                sum++;
            }

            if (0 == sum) {
                max = i + 1;
            } else if (0 != hash[sum + n]) {
                max = Math.max(max, i + 1 - hash[sum + n]);
            } else {
                // 使用i + 1 而不是 i 能避免 index为0时候不计算res的情况
                hash[sum + n] = i + 1;
            }
        }

        return max;
    }

    /**
     * 解
     * 思路：将0转为-1，前缀和
     *
     * @param nums 连续数组
     * @return 解
     */
    private static int findMaxLength2(int[] nums) {
        int length = nums.length;
        if (length < 2) {
            return 0;
        }

        int max = 0;
        int sum = 0;
        Map<Integer, Integer> sumIndex = new HashMap<>();
        // 从0开始到n 的和为0的时候 会用到
        // 避免每次判断0 == sum
        sumIndex.put(0, -1);

        for (int i = 0; i < length; i++) {
            if (0 == nums[i]) {
                sum--;
            } else {
                sum++;
            }
            Integer key = sum;
            // 计算max
            Integer targetIndex = sumIndex.get(key);
            if (null != targetIndex) {
                int tempMax = i - targetIndex;
                if (tempMax > max) {
                    max = tempMax;
                }
            } else {
                // 放入map
                sumIndex.put(key, i);
            }
        }

        return max;
    }
}
