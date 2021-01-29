package com.clei.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.Scanner;

/**
 * 用于生产一些偶尔会用到的json字符串
 */
public class JSONUtil {

    /**
     * 快速生产一个自定义json
     * @return
     * @throws Exception
     */
    public static JSONObject getJSON() throws Exception{
        Scanner input = new Scanner(System.in);
        JSONObject json = new JSONObject();

        String str = input.nextLine();

        while (!"xxx".equals(str)){
            int i = str.indexOf(' ');

            json.put(str.substring(0,i),str.substring(i + 1));

            str = input.nextLine();
        }

        return json;
    }

    public static void main(String[] args) throws Exception{

        PrintUtil.log(getJSON());

    }

}
