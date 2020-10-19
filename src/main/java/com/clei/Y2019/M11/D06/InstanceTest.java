package com.clei.Y2019.M11.D06;

import com.clei.utils.PrintUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InstanceTest {
    public static void main(String[] args) {

        String endDate = "2019-11-01";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate eDate = LocalDate.parse(endDate, df);

        PrintUtil.dateLine(ChronoUnit.DAYS.between(eDate, LocalDate.now()));
        PrintUtil.dateLine(ChronoUnit.DAYS.between(LocalDate.now(), eDate));


        Object oo = null;
        PrintUtil.dateLine(("" + oo).equals("null"));


        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");

        String[] array = new String[]{"aa", "bb", "cc"};

        Object obj = list;
        Object obj2 = array;

        PrintUtil.dateLine(obj.getClass().getCanonicalName());
        PrintUtil.dateLine(obj2.getClass().getCanonicalName());

        Collection c = (Collection) obj;
        PrintUtil.dateLine(c.size());

        String[] arr = (String[]) obj2;
        PrintUtil.dateLine(arr.length);

        PrintUtil.dateLine(obj instanceof Collection);
        PrintUtil.dateLine(obj instanceof List);
        PrintUtil.dateLine(obj instanceof ArrayList);

        PrintUtil.dateLine(obj2 instanceof String);
    }
}
