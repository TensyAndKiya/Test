package com.clei.utils;

import java.util.Scanner;

/**
 * 用于快速得到16进制转10进制
 * 或10进制转16进制数字
 */
public class HexUtil {
    public static void main(String[] args) {
        while (true){
            System.out.println("请输入数字(10或16进制，16进制以0x开头，输入x结束) ：");
            Scanner input = new Scanner(System.in);
            String str = input.next();
            if(str.startsWith("x")){
                break;
            }
            if(str.startsWith("0x")){
                System.out.println(Integer.parseInt(str.substring(2),16));
            }else {
                System.out.println("0x" + Integer.toHexString(Integer.parseInt(str)));
            }
        }
    }
}
