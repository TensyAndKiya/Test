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

        PrintUtil.log(ChronoUnit.DAYS.between(eDate, LocalDate.now()));
        PrintUtil.log(ChronoUnit.DAYS.between(LocalDate.now(), eDate));


        Object oo = null;
        PrintUtil.log(("" + oo).equals("null"));


        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");

        String[] array = new String[]{"aa", "bb", "cc"};

        Object obj = list;
        Object obj2 = array;

        PrintUtil.log(obj.getClass().getCanonicalName());
        PrintUtil.log(obj2.getClass().getCanonicalName());

        Collection c = (Collection) obj;
        PrintUtil.log(c.size());

        String[] arr = (String[]) obj2;
        PrintUtil.log(arr.length);

        PrintUtil.log(obj instanceof Collection);
        PrintUtil.log(obj instanceof List);
        PrintUtil.log(obj instanceof ArrayList);

        PrintUtil.log(obj2 instanceof String);
    }
}
