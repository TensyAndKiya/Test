package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 宝石与石头
 * 给定字符串J 代表石头中宝石的类型，和字符串 S代表你拥有的石头。 S 中每个字符代表了一种你拥有的石头的类型，你想知道你拥有的石头中有多少是宝石。
 * <p>
 * J 中的字母不重复，J 和 S中的所有字符都是字母。字母区分大小写，因此"a"和"A"是不同类型的石头。
 * <p>
 * 示例 1:
 * <p>
 * 输入: J = "aA", S = "aAAbbbb"
 * 输出: 3
 * 示例 2:
 * <p>
 * 输入: J = "z", S = "ZZ"
 * 输出: 0
 *
 * @author KIyA
 */
public class Algorithm0771 {

    public static void main(String[] args) {
        PrintUtil.log(numJewelsInStones("aA", "aAAbbbb"));
    }

    private static int numJewelsInStones(String jewels, String stones) {
        char[] arr1 = jewels.toCharArray();
        int[] map = new int[128];
        for (char c : arr1) {
            map[c] = 1;
        }
        char[] arr2 = stones.toCharArray();
        int count = 0;
        for (char c : arr2) {
            if (map[c] == 1) {
                count++;
            }
        }
        return count;
    }
}