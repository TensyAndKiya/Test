package com.clei.Y2019.M12.D25;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test2 {
    public static void main(String[] args) {
        String temp = "我勒个去;";
        PrintUtil.log(Arrays.toString(temp.split(";")));

        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        List<String> list2 = new ArrayList<>();
        list1.add("5");
        list1.add("6");
        list1.add("7");

        list1.addAll(list2);

        for (int i = 0; i < list1.size(); i++) {
            PrintUtil.log(list1.get(i));
        }

        String str1 = "aabb[tab]cccc";

        str1 = str1.replaceAll("\\[tab\\]","\t");

        PrintUtil.log(str1);

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
            // PrintUtil.log(s);
            if(!k1.contains(s)){
                PrintUtil.log(s);
            }
        }
    }
}
