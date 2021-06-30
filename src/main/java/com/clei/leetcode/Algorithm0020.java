package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 有效的括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "()"
 * 输出：true
 * 示例 2：
 * <p>
 * 输入：s = "()[]{}"
 * 输出：true
 * 示例 3：
 * <p>
 * 输入：s = "(]"
 * 输出：false
 * 示例 4：
 * <p>
 * 输入：s = "([)]"
 * 输出：false
 * 示例 5：
 * <p>
 * 输入：s = "{[]}"
 * 输出：true
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= s.length <= 104
 * s 仅由括号 '()[]{}' 组成
 *
 * @author KIyA
 */
public class Algorithm0020 {

    public static void main(String[] args) {
        PrintUtil.log(isValid("[[{}{}[](([]))]]"));
    }

    private static boolean isValid(String s) {
        int length = s.length();
        // 奇数
        if (1 == (1 & s.length())) {
            return false;
        }
        int half = length / 2;
        char[] arr = s.toCharArray();
        // 这里使用数组作为栈比使用LinkedList更快
        char[] stack = new char[half];
        int index = 0;
        for (char c : arr) {
            switch (c) {
                case '(':
                case '[':
                case '{':
                    if (index == half) {
                        return false;
                    }
                    stack[index++] = c;
                    break;
                case ')':
                    if (0 == index) {
                        return false;
                    }
                    char cc = stack[--index];
                    if ('(' != cc) {
                        return false;
                    }
                    break;
                case ']':
                    if (0 == index) {
                        return false;
                    }
                    char ccc = stack[--index];
                    if ('[' != ccc) {
                        return false;
                    }
                    break;
                case '}':
                    if (0 == index) {
                        return false;
                    }
                    char cccc = stack[--index];
                    if ('{' != cccc) {
                        return false;
                    }
                    break;
            }
        }
        return 0 == index;
    }
}
