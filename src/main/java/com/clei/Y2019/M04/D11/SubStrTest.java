package com.clei.Y2019.M04.D11;

import com.clei.utils.PrintUtil;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SubStrTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
        int length = str.length();
        int max = 0;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length + 1; j++) {
                String tempStr = str.substring(i, j);
                Set<Character> set = new HashSet<>();
                for (char c : tempStr.toCharArray()) {
                    set.add(c);
                }
                if (tempStr.length() - set.size() > 0) {
                    break;
                }
                int leng = tempStr.length();
                max = max > leng ? max : leng;
            }
        }
        PrintUtil.dateLine("RESULT: " + max);
    }
}
