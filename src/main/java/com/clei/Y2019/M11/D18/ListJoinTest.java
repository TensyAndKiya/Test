package com.clei.Y2019.M11.D18;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.PrintUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListJoinTest {
    public static void main(String[] args) {

        BigDecimal b = new BigDecimal("0.01");
        BigDecimal c = BigDecimal.ZERO;
        PrintUtil.log(b.compareTo(c));

        float f = 0.051314f;
        f = new BigDecimal(f).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        PrintUtil.log(f == 0.05f);

        List<Map<String,Object>> l = new ArrayList<>();
        Map<String,Object> m = new HashMap<>();
        m.put("parkId","000000");
        m.put("occupyType",0);
        m.put("occupyNum",1);
        l.add(m);
        Map<String,Object> m1 = new HashMap<>();
        m1.put("parkId","000001");
        m1.put("occupyType",1);
        m1.put("occupyNum",3);
        l.add(m1);
        Map<String,Object> m2 = new HashMap<>();
        m2.put("parkId","000000");
        m2.put("occupyType",0);
        m2.put("occupyNum",2);
        l.add(m2);
        PrintUtil.log(JSONObject.toJSONString(l));


        List<String> list = new ArrayList<>();
        list.add(null);
        list.add("aa");
        list.add(null);
        list.add("bb");
        list.add(null);
        PrintUtil.log(list.stream().filter(v -> null != v).collect(Collectors.joining(",")));

        List<String> ll = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ll.add("xxx" + i);
        }

        for (int i = 0; i < 10; i++) {
            List<String> lll = ll.subList(i * 10, (i + 1) * 10);
            PrintUtil.log(lll.stream().collect(Collectors.joining(",")));
            PrintUtil.log("------------------------------------------------------");
        }

    }
}
