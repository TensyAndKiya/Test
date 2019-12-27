package com.clei.Y2019.M12.D25;

import com.alibaba.fastjson.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class Test2 {
    public static void main(String[] args) {
        JSONObject json1 = JSONObject.parseObject("{\"XMMC\":\"车辆停放服务\",\"ZXBM\":\"\",\"LSLBS\":\"\",\"XMSL\":\"1\",\"ZZSTSGL\":\"\",\"SPBM\":\"3040502020200000000\",\"GGXH\":\"\",\"XMDJ\":\"1.00\",\"SE\":\"0.08\",\"DW\":\"\",\"YHZCBS\":\"\",\"XMJE\":\"1.00\",\"SL\":\"0.09\",\"BY2\":\"\",\"BY1\":\"\",\"BY4\":\"\",\"BY3\":\"\",\"FPHXZ\":\"0\",\"BY5\":\"\"}");
        JSONObject json2 = JSONObject.parseObject("{\"FPHXZ\": \"0\",\"SPBM\": \"1010101010000000000\",\"ZXBM\": \"\",\"YHZCBS\": \"\",\"LSLBS\": \"\",\"ZZSTSGL\": \"\",\"XMMC\": \"东北大米\",\"GGXH\": \"500克\",\"DW\": \"袋\",\"XMSL\": \"1\",\"XMDJ\": \"50\",\"XMJE\": \"50\",\"SL\": \"0.17\",\"SE\": \"8.5\",\"BY1\": \"\",\"BY2\": \"\",\"BY3\": \"\",\"BY4\": \"\",\"BY5\": \"\"}");

        Set<String> k1 = new HashSet<>();
        json1.forEach((k,v) -> {
            k1.add(k);
        });
        Set<String> k2 = new HashSet<>();
        json2.forEach((k,v) -> {
            k2.add(k);
        });

        for (String s : k2){
            // System.out.println(s);
            if(!k1.contains(s)){
                System.out.println(s);
            }
        }



    }
}
