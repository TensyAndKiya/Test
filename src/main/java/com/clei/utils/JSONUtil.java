package com.clei.utils;

import com.alibaba.fastjson.JSONObject;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 用于生产一些偶尔会用到的json字符串
 */
public class JSONUtil {

    public static void main(String[] args) {

        System.out.println(ChronoUnit.SECONDS.between(LocalDateTime.now(),LocalDateTime.now().plusMinutes(1)));

        String[] keys = {"no","assetsName","assetsIp","assetsHolder","assetsUdid","assetsCategory","assetsProducer",
                "assetsSystem","unitName","departmentName","assetsRunState"};

        JSONObject json = new JSONObject();

        json.put("no",1);
        json.put("assetsName","aa");
        json.put("assetsIp","bb");
        json.put("assetsHolder","cc");
        json.put("assetsUdid","ee");
        json.put("assetsCategory",0);
        json.put("assetsProducer",1);
        json.put("assetsSystem",1);
        json.put("unitName","开发");
        json.put("departmentName","方法");
        json.put("assetsRunState",1);

        System.out.println(json.toJSONString());

    }

}
