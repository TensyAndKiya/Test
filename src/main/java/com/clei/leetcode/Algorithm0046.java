package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 全排列
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * 示例 2：
 * <p>
 * 输入：nums = [0,1]
 * 输出：[[0,1],[1,0]]
 * 示例 3：
 * <p>
 * 输入：nums = [1]
 * 输出：[[1]]
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= nums.length <= 6
 * -10 <= nums[i] <= 10
 * nums 中的所有整数 互不相同
 *
 * @author KIyA
 */
public class Algorithm0046 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        long start = System.currentTimeMillis();
        permute1(nums);
        long end = System.currentTimeMillis();
        PrintUtil.log(" {} ms ", end - start);
        start = System.currentTimeMillis();
        permute2(nums);
        end = System.currentTimeMillis();
        PrintUtil.log(" {} ms ", end - start);
    }

    /**
     * 方法1
     *
     * @param nums 数组
     * @return
     */
    private static List<List<Integer>> permute1(int[] nums) {
        int length = nums.length;
        List<List<Integer>> resultList = new ArrayList<>(getResultLength(length));
        List<Integer> numList = new ArrayList<>(length);
        for (int num : nums) {
            numList.add(num);
        }
        getList1(resultList, numList, 0, length);
        return resultList;
    }

    private static void getList1(List<List<Integer>> resultList, List<Integer> list, int position, int size) {
        if (position == size) {
            resultList.add(new ArrayList<>(list));
        } else {
            for (int i = position; i < size; i++) {
                // 把用了的数字放到左边
                Collections.swap(list, i, position);
                getList1(resultList, list, position + 1, size);
                // 还原
                Collections.swap(list, i, position);
            }
        }
    }

    /**
     * 方法2
     *
     * @param nums 数组
     * @return
     */
    private static List<List<Integer>> permute2(int[] nums) {
        int length = nums.length;
        List<List<Integer>> resultList = new ArrayList<>(getResultLength(length));
        boolean[] used = new boolean[length];
        int[] tempNum = new int[length];
        getList2(resultList, nums, tempNum, used, 0);
        return resultList;
    }

    private static void getList2(List<List<Integer>> resultList, int[] nums, int[] tempNum, boolean[] used, int position) {
        if (position == tempNum.length) {
            List<Integer> result = new ArrayList<>(tempNum.length);
            for (int i : tempNum) {
                result.add(i);
            }
            resultList.add(result);
        } else {
            for (int i = 0; i < tempNum.length; i++) {
                if (used[i]) {
                    continue;
                }
                used[i] = true;
                tempNum[position] = nums[i];
                // 下一个
                getList2(resultList, nums, tempNum, used, position + 1);
                // 还原
                used[i] = false;
            }
        }
    }

    private static int getResultLength(int n) {
        int result = 1;
        while (n > 1) {
            result *= n;
            n--;
        }
        return result;
    }
}