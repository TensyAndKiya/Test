package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.ArrayList;
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
        int[] arr = {-1, 0, 1, 2, -1, -4};
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
        // TODO
        return result;
    }

}
