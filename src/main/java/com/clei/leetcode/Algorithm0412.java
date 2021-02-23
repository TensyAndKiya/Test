package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Fizz Buzz
 * 写一个程序，输出从 1 到 n 数字的字符串表示。
 * <p>
 * 1. 如果 n 是3的倍数，输出“Fizz”；
 * <p>
 * 2. 如果 n 是5的倍数，输出“Buzz”；
 * <p>
 * 3.如果 n 同时是3和5的倍数，输出 “FizzBuzz”。
 * <p>
 * 示例：
 * <p>
 * n = 15,
 * <p>
 * 返回:
 * [
 * "1",
 * "2",
 * "Fizz",
 * "4",
 * "Buzz",
 * "Fizz",
 * "7",
 * "8",
 * "Fizz",
 * "Buzz",
 * "11",
 * "Fizz",
 * "13",
 * "14",
 * "FizzBuzz"
 * ]
 *
 * @author KIyA
 */
public class Algorithm0412 {

    public static void main(String[] args) {
        PrintUtil.log(fizzBuzz(15));
    }

    private static List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<>(n);
        n = n + 1;
        for (int i = 1; i < n; i++) {
            if (i % 5 == 0) {
                if (i % 3 == 0) {
                    list.add("FizzBuzz");
                } else {
                    list.add("Buzz");
                }
            } else if (i % 3 == 0) {
                list.add("Fizz");
            } else {
                list.add(String.valueOf(i));
            }
        }
        return list;
    }
}
