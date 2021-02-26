package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三数之和
 * <p>
 * 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 示例：
 * <p>
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * <p>
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/3sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author KIyA
 */
public class Algorithm0015 {

    public static void main(String[] args) {
        int[] arr = {3, 0, -2, -1, 1, 2};
        List<List<Integer>> result = threeSum(arr);
        for (List<Integer> l : result) {
            PrintUtil.log(l);
        }
    }

    private static List<List<Integer>> threeSum(int[] nums) {
        int length = nums.length;
        if (length < 3) {
            return new ArrayList<>(0);
        }
        List<List<Integer>> result = new ArrayList<>();
        // 排序
        Arrays.sort(nums);
        // 循环一
        for (int i = 0; i < length; i++) {
            // 第一个数都大于0 无解
            if (nums[i] > 0) {
                break;
            }
            // 保证与上次不同
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int target = -nums[i];
            int k = length - 1;
            // 循环二
            for (int j = i + 1; j < length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                // k值更改 保证j在k左
                while (j < k && nums[j] + nums[k] > target) {
                    k--;
                }
                if (j == k) {
                    break;
                }
                if (nums[j] + nums[k] == target) {
                    List<Integer> l = new ArrayList<>(3);
                    l.add(nums[i]);
                    l.add(nums[j]);
                    l.add(nums[k]);
                    result.add(l);
                }
            }
        }
        return result;
    }

}
