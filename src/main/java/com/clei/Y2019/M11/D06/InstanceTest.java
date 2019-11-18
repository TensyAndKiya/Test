package com.clei.Y2019.M11.D06;

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
        LocalDate eDate = LocalDate.parse(endDate,df);

        System.out.println(ChronoUnit.DAYS.between(eDate,LocalDate.now()));
        System.out.println(ChronoUnit.DAYS.between(LocalDate.now(),eDate));


        Object oo = null;
        System.out.println(("" + oo).equals("null"));


        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");

        String[] array = new String[]{"aa","bb","cc"};

        Object obj = list;
        Object obj2 = array;

        System.out.println(obj.getClass().getCanonicalName());
        System.out.println(obj2.getClass().getCanonicalName());

        Collection c = (Collection) obj;
        System.out.println(c.size());

        String[] arr = (String[]) obj2;
        System.out.println(arr.length);

        System.out.println(obj instanceof Collection);
        System.out.println(obj instanceof List);
        System.out.println(obj instanceof ArrayList);

        System.out.println(obj2 instanceof String);
    }
}
