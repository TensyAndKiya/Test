package com.clei.Y2019.M10.D23;

import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NothingTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("aa");
        list.add("aa");
        list.add("aa");
        PrintUtil.dateLine(list);
        PrintUtil.dateLine(new Date(1571742900000L));
    }
}
