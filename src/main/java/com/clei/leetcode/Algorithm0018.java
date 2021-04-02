package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 四数之和
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
 * <p>
 * 注意：答案中不可以包含重复的四元组。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * 示例 2：
 * <p>
 * 输入：nums = [], target = 0
 * 输出：[]
 *
 * @author KIyA
 */
public class Algorithm0018 {

    public static void main(String[] args) {
        int[] nums = {1, -2, -5, -4, -3, 3, 3, 5};
        int target = -11;
        fourSum(nums, target).forEach(PrintUtil::log);
    }

    private static List<List<Integer>> fourSum(int[] nums, int target) {
        int length = nums.length;
        if (length < 4) {
            return Collections.emptyList();
        }
        // 排序
        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        end:
        for (int i = 0; i < length - 3; i++) {

            // 避免重复
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 快速结束
            if (nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }
            if (nums[i] + nums[length - 3] + nums[length - 2] + nums[length - 1] < target) {
                continue;
            }

            for (int j = i + 1; j < length - 2; j++) {

                // 避免重复
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                // 快速结束
                if (nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }
                if (nums[i] + nums[j] + nums[length - 2] + nums[length - 1] < target) {
                    continue;
                }

                int realTarget = target - nums[j] - nums[i];
                int l = length - 1;

                for (int k = j + 1; k < length - 1; k++) {

                    // 避免重复
                    if (k > j + 1 && nums[k] == nums[k - 1]) {
                        continue;
                    }

                    // l值更改 保证k在l左
                    while (k < l && nums[k] + nums[l] > realTarget) {
                        l--;
                    }
                    if (k == l) {
                        break;
                    }

                    if (nums[k] + nums[l] == realTarget) {
                        List<Integer> list = new ArrayList<>(3);
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[k]);
                        list.add(nums[l]);
                        result.add(list);
                    }
                }
            }
        }
        return result;
    }
}
