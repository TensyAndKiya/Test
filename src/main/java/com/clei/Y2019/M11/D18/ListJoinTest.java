package com.clei.Y2019.M11.D18;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListJoinTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add(null);
        list.add("aa");
        list.add(null);
        list.add("bb");
        list.add(null);
        System.out.println(list.stream().filter(v -> null != v).collect(Collectors.joining(",")));

        List<String> l = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            l.add("xxx" + i);
        }

        for (int i = 0; i < 10; i++) {
            List<String> ll = l.subList(i * 10,(i + 1) * 10);
            System.out.println(ll.stream().collect(Collectors.joining(",")));
            System.out.println("------------------------------------------------------");
        }

    }
}
