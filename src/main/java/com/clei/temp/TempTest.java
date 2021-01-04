package com.clei.temp;

import com.clei.entity.Person;
import com.clei.utils.PrintUtil;

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
        float a = 0.000f;
        float b = 0.0000f;
        float c = 0.00000f;
        PrintUtil.log(c);
        PrintUtil.log("a == b : {}", Float.compare(a, b));
        PrintUtil.log("b == c : {}", Float.compare(b, c));
        PrintUtil.log("a == c : {}", Float.compare(a, c));

        Person p = new Person("张三", 1, 2);
        PrintUtil.log(p);
        tt(p::setName, "李四");
        PrintUtil.log(p);

        List<DateTimeVehicleCount> list = new ArrayList<>();

        list.add(new DateTimeVehicleCount("2020-12-14 18:32:00"));
        list.add(new DateTimeVehicleCount("2020-12-14 18:10:00"));
        list.add(new DateTimeVehicleCount("2020-12-14 17:46:00"));
        list.add(new DateTimeVehicleCount("2020-12-14 17:48:00"));
        list.forEach(d -> PrintUtil.log(d.getDateTime()));
        PrintUtil.separatorLine();
        list = list.stream().sorted(Comparator.comparing(DateTimeVehicleCount::getDateTime)).collect(Collectors.toList());
        list.forEach(d -> PrintUtil.log(d.getDateTime()));
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
