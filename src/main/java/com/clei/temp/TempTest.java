package com.clei.temp;

import com.alibaba.fastjson.JSONObject;
import com.clei.entity.Person;
import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    public static void main(String[] args) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject();
        json.put("userId", 1234L);
        json.put("mobile", "18408244077");
        json.put("email", "yueyaye@163.com");
        json.put("username", "hasaki");
        json.put("groupCode", "9527");
        PrintUtil.log(json.toJSONString());
        PrintUtil.log(URLEncoder.encode(json.toJSONString(), "UTF-8"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minusMonths(5);
        PrintUtil.log(DateUtil.format(now));
        PrintUtil.log(DateUtil.format(before));

        System.getenv().forEach((k, v) -> PrintUtil.log("k :{}, v: {}", k, v));

        PrintUtil.log(ChronoUnit.SECONDS.between(LocalDateTime.now(), LocalDateTime.of(2021, 2, 9, 18, 30)));

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
