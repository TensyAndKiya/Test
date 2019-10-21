package com.clei.Y2019.M04.D30;

import java.util.Scanner;

public class Hex2TenTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in,"UTF-8");
        System.out.println("输入16进制：");
        String number = input.nextLine();
        while (!"x".equals(number)){
            Integer num = Integer.valueOf(number,16);
            System.out.println(num);
            System.out.println("输入16进制：");
            number = input.nextLine();
        }
        input.close();
    }
}
