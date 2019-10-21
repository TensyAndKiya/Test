package com.clei.Y2019.M04.D24;

import java.util.Scanner;

public class Str2JSONStrTest {
    private static final String NOTIFY_URL = "http://111.11.11.111:1111/park/cdPayNotify.jsp";

    public static void main(String[] args) {
        System.out.println("请输入字符串：");
        Scanner input = new Scanner(System.in,"UTF-8");
        String str = input.nextLine();

        str = replace(str,'{');
        str = replace(str,'}');

        str = str.replaceAll(", ","&");

        String[] kv = str.split(", ");
        StringBuilder sb = new StringBuilder("?");
        for(String s : kv){
            sb.append(s + '&');
        }
        String temp = sb.toString();
        temp = temp.substring(0,temp.length()-1);
        System.out.println(NOTIFY_URL+temp);

        /*for(String s : kv){
            int index = s.indexOf("=");
            String k = s.substring(0,index);
            String v = s.substring(index + 1);
            if("null".equals(v)){
                v = "";
            }
            map.put(k,v);
        }

        System.out.println("{");
        map.forEach( (k,v) -> {
            System.out.println("\t"+"\"" + k + "\":\"" + v + "\",");
        } );
        System.out.println("}");*/
    }

    private static String replace(String str,char c){
        for(int i = 0 ;i<str.length();i++){
            if(str.charAt(i) == c){
                String temp = str.substring(0,i) + str.substring(i + 1);
                return temp;
            }
        }
        return "";
    }
}
