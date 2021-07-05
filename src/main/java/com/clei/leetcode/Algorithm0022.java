package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 括号生成
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * <p>
 * 示例 1：
 * <p>
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 * 示例 2：
 * <p>
 * 输入：n = 1
 * 输出：["()"]
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= n <= 8
 *
 * @author KIyA
 */
public class Algorithm0022 {

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            List<String> result = generateParenthesis(i);
            PrintUtil.log("n : {}, resultSize : {}", i, result.size());
        }
    }

    private static List<String> generateParenthesis(int n) {
        if (n < 1) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        char[] arr = new char[n * 2];
        generateParenthesis(0, 0, result, arr, 0);
        return result;
    }

    private static void generateParenthesis(int left, int right, List<String> result, char[] arr, int index) {
        if (index == arr.length) {
            result.add(new String(arr));
            return;
        }
        if (right < left) {
            arr[index] = ')';
            generateParenthesis(left, right + 1, result, arr, index + 1);
        }

        if (left < arr.length / 2) {
            arr[index] = '(';
            generateParenthesis(left + 1, right, result, arr, index + 1);
        }
    }
}
