package com.clei.Y2019.M04.D22;

import com.clei.utils.PrintUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StringCompareTest {
    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        list.add("sDate");
        list.add("sdate");
        list.add("sdatetime");
        list.add("sDatetime");
        PrintUtil.log(list);
        Collections.sort(list, new MyStrComparator());
        PrintUtil.log(list);
    }

    private static class MyStrComparator implements Comparator<String>, Serializable {

        private static final long serialVersionUID = 6294659288812531952L;

        @Override
        public int compare(String str1, String str2) {
            return str1.toLowerCase().compareTo(str2.toLowerCase());
        }
    }
}
