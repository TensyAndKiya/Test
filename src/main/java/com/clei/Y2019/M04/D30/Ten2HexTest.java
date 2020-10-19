package com.clei.Y2019.M04.D30;

import com.clei.utils.PrintUtil;

import java.util.Scanner;

public class Ten2HexTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in,"UTF-8");
        PrintUtil.dateLine("输入10进制：");
        String number = input.nextLine();
        while (!"x".equals(number)) {
            String num = Integer.toHexString(Integer.parseInt(number));
            PrintUtil.dateLine(num);
            PrintUtil.dateLine("输入10进制：");
            number = input.nextLine();
        }
        input.close();
    }
}
