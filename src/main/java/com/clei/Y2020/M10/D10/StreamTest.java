package com.clei.Y2020.M10.D10;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 对流操作还不太熟练啊
 *
 * @author KIyA
 */
public class StreamTest {

    private static String[] ignoredInterfaces = {};

    public static void main(String[] args) {

        System.out.println(falseMethod(1) || falseMethod(2) && trueMethod(1));

        List<String> list = Stream.of("one", "two", "three", "four")
                /*.filter(e -> e.length() > 3)
                .peek(e -> PrintUtil.println(e))
                .map(String::toUpperCase)
                .peek(e -> PrintUtil.println(e))*/
                .skip(0)
                .collect(Collectors.toList());

        PrintUtil.dateLine();
        list.forEach(PrintUtil::println);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        long millis = System.currentTimeMillis();
        System.out.println(millis);
        System.out.println(DateUtil.formatMillis(millis, dtf));

    }

    public static boolean trueMethod(int i) {
        System.out.println("" + i + true);
        return true;
    }

    public static boolean falseMethod(int i) {
        System.out.println("" + i + false);
        return false;
    }


}
