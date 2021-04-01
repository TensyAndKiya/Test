package com.clei.temp;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 临时的小段代码的测试2
 *
 * @author KIyA
 */
public class TempTest2 {

    public static void main(String[] args) {

        System.out.println((2L << 41) / 1024 / 1024 / 1024 / 1024);

        PrintUtil.log(3 * 0.1 == 0.3);

        System.out.println(0.1 * 3);

        PrintUtil.log(Float.valueOf("40.5F"));
        PrintUtil.log(Double.valueOf("40.5D"));
        // PrintUtil.log(Long.valueOf("40.5"));

        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(3);
        list2.add(4);
        // 交集
        list1.retainAll(list2);
        PrintUtil.log(list1);
        //
        PrintUtil.log(DateUtil.formatMillis(1615172083862L));

        PrintUtil.log(DateUtil.toEpochSecond("2021-03-05 18:08:08"));
        PrintUtil.log(DateUtil.toEpochMilli("2021-03-05 18:08:08"));

        List<Obj> list = new ArrayList<>();
        list.add(new Obj(new BigDecimal("3.88"), 1, "b"));
        list.add(new Obj(new BigDecimal("4.99"), 3, "d"));
        list.add(new Obj(new BigDecimal("4.99"), 2, "d"));
        list.add(new Obj(new BigDecimal("4.99"), 2, "e"));
        list.add(new Obj(new BigDecimal("1.22"), 4, "a"));


        Comparator<Obj> com1 = (a, b) -> b.getA().compareTo(a.getA());
        com1 = com1.thenComparing((a, b) -> b.getB().compareTo(a.getB()));
        com1 = com1.thenComparing(Obj::getC);

        list = list.stream().sorted(com1).collect(Collectors.toList());
        for (Obj obj : list) {
            PrintUtil.log(obj.toString());
        }
    }

    private static class Obj {

        private BigDecimal a;
        private Integer b;
        private String c;

        public BigDecimal getA() {
            return a;
        }

        public void setA(BigDecimal a) {
            this.a = a;
        }

        public Integer getB() {
            return b;
        }

        public void setB(Integer b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public Obj(BigDecimal a, Integer b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String toString() {
            return "Obj{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c='" + c + '\'' +
                    '}';
        }
    }
}
