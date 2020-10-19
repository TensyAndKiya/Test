package com.clei.Y2020.M01.D20;

import com.clei.utils.PrintUtil;

import java.util.Set;
import java.util.TreeSet;

/**
 * 得到两个月份间的所有月份
 */
public class NothingTest {
    public static void main(String[] args) {
        int st = 201912;
        int et = 202001;

        Set<Integer> dateMonth = new TreeSet<>();
        int startYear = st / 100;
        int endYear = et / 100;
        int startMonth = st % 100;
        int endMonth = et % 100;
        for(int i = startYear, j = startMonth; (i < endYear || (i == endYear && j <= endMonth));){
            dateMonth.add(i * 100 + j);
            j++;
            if(j > 12){
                i++;
                j = 1;
            }
        }

        PrintUtil.dateLine(dateMonth);
    }
}
