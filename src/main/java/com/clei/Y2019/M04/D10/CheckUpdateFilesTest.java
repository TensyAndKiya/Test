package com.clei.Y2019.M04.D10;

import com.clei.utils.PrintUtil;

import java.util.Scanner;

/**
 * 看类名方法名
 * @author kiya
 * @since 2019 04 15
 */
public class CheckUpdateFilesTest {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in,"UTF-8");
        StringBuilder sb = new StringBuilder("");
        String str = input.nextLine();
        while ( !str.contains("no changes added to commit") ){
            sb.append(str);
            str = input.nextLine();
        }
        checkConfig(sb);
    }

    private static void checkConfig(StringBuilder sb){
        int count = 0;
        if(sb.indexOf("AlipayConfig.java") > -1){
            count ++;
        }else{
            PrintUtil.dateLine("AlipayConfig.java");
        }
        if(sb.indexOf("AlipayWXConfig.java") > -1){
            count ++;
        }else{
            PrintUtil.dateLine("AlipayWXConfig.java");
        }
        if(sb.indexOf("CdPayConfig.java") > -1){
            count ++;
        }else{
            PrintUtil.dateLine("CdPayConfig.java");
        }
        if(sb.indexOf("application.properties") > -1){
            count ++;
        }else{
            PrintUtil.dateLine("application.properties");
        }
        if( count == 4 ){
            PrintUtil.dateLine("PASS!");
        }else{
            PrintUtil.dateLine("ERROR!WARNING!ERROR!WARNING!ERROR!WARNING!ERROR!WARNING!");
        }
    }
}
