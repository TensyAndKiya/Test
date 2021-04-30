package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 最长公共前缀
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * <p>
 * 如果不存在公共前缀，返回空字符串 ""。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * 示例 2：
 * <p>
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 *
 * @author KIyA
 */
public class Algorithm0014 {

    public static void main(String[] args) {
        String[] arr = {"flower", "flower"};
        PrintUtil.log(longestCommonPrefix(arr));
    }

    private static String longestCommonPrefix(String[] strs) {
        int length = strs.length;
        if (0 == length) {
            return "";
        }
        if (1 == length) {
            return strs[0];
        }
        String str = strs[0];
        char[] arr = str.toCharArray();
        int length2 = arr.length;
        int endIndex = 0;
        outer:
        for (int i = 0; i < length2; i++) {
            char c = arr[i];
            for (int j = 1; j < length; j++) {
                if (strs[j].length() < i + 1 || c != strs[j].charAt(i)) {
                    break outer;
                }
                endIndex++;
            }
        }
        return str.substring(0, endIndex);
    }
}
