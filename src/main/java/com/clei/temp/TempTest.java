package com.clei.temp;

import com.clei.entity.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 临时的小段代码的测试
 *
 * @author KIyA
 */
public class TempTest {

    public static void main(String[] args) {


        Person p = new Person("张三", 1, 2);
        System.out.println(p);
        tt(p::setName, "李四");
        System.out.println(p);


        List<DateTimeVehicleCount> list = new ArrayList<>();

        list.add(new DateTimeVehicleCount("2020-12-14 18:32:00"));
        list.add(new DateTimeVehicleCount("2020-12-14 18:10:00"));
        list.add(new DateTimeVehicleCount("2020-12-14 17:46:00"));
        list.add(new DateTimeVehicleCount("2020-12-14 17:48:00"));

        list.forEach(l -> {
            System.out.println(l.getDateTime());
        });

        list = list.stream().sorted(Comparator.comparing(DateTimeVehicleCount::getDateTime)).collect(Collectors.toList());

        list.forEach(l -> {
            System.out.println(l.getDateTime());
        });

    }


    public static class DateTimeVehicleCount {

        /**
         * 时间
         */
        private String dateTime;

        private String getDateTime() {
            return this.dateTime;
        }

        public DateTimeVehicleCount(String dateTime) {
            this.dateTime = dateTime;
        }
    }

    private static <T> void tt(Consumer<T> cc, T t) {
        cc.accept(t);
    }

}
