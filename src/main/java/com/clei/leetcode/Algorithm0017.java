package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电话号码的字母组合
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 * <p>
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：digits = "23"
 * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * 示例 2：
 * <p>
 * 输入：digits = ""
 * 输出：[]
 * 示例 3：
 * <p>
 * 输入：digits = "2"
 * 输出：["a","b","c"]
 *
 * @author KIyA
 */
public class Algorithm0017 {


    public static void main(String[] args) {
        letterCombinations("23").forEach(PrintUtil::log);
    }

    private static List<String> letterCombinations(String digits) {
        int length = digits.length();
        if (0 == length) {
            return Collections.emptyList();
        }
        int capacity = (int) Math.pow(3, length);
        List<String> result = new ArrayList<>(capacity);
        Map<Character, String> phoneMap = new HashMap<Character, String>(8) {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        letterCombinations(result, phoneMap, digits, 0, length, new StringBuilder());
        return result;
    }

    private static void letterCombinations(List<String> list, Map<Character, String> phoneMap, String digits, int index, int length, StringBuilder sb) {
        if (index == length) {
            list.add(sb.toString());
        } else {
            char c = digits.charAt(index);
            String chars = phoneMap.get(c);
            char[] arr = chars.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]);
                letterCombinations(list, phoneMap, digits, index + 1, length, sb);
                sb.deleteCharAt(index);
            }
        }
    }
}
