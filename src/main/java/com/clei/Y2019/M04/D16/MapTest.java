package com.clei.Y2019.M04.D16;

import com.clei.utils.PrintUtil;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    public static void main(String[] args){
        Map<String,String> map = new HashMap<>();
        PrintUtil.dateLine(null == map.get("access"));
    }
}
