package com.clei.Y2020.M10.D10;

import com.clei.entity.Person;
import com.clei.utils.PrintUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 对流操作还不太熟练啊
 *
 * @author KIyA
 */
public class StreamTest {

    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        list.add(new Person("张三", 18, 1));
        list.add(new Person("李四", 19, 0));
        list.add(new Person("李端", 22, 1));
        list.add(new Person("王五", 20, 1));
        list.add(new Person("赵秦", 25, 0));
        list.add(new Person("张泽", 21, 0));
        list.add(new Person("王炸", 23, 0));
        list.add(new Person("陈某", 24, 1));

        PrintUtil.log(falseMethod(1) || falseMethod(2) && trueMethod(1));

        Stream.iterate(0, i -> i + 2).limit(10).forEach(PrintUtil::log);

        Stream.generate(Math::random).limit(3).forEach(PrintUtil::log);

        // 找到第一个大于9的
        PrintUtil.log(Stream.iterate(0, i -> i + 2).limit(10).filter(i -> i > 9).findFirst().get());

        // 找到任意一个大于9的
        PrintUtil.log(Stream.iterate(0, i -> i + 2).limit(10).parallel().filter(i -> i > 9).findAny().get());

        // 是否有大于9的值
        PrintUtil.log(Stream.iterate(0, i -> i + 2).limit(10).anyMatch(i -> i > 9));

        // 最大值
        // 若要取最小值可用min 或使用Comparator.comparingInt(i -> -i) 或使用Comparator.reverseOrder()
        PrintUtil.log(Stream.iterate(0, i -> i + 1).limit(10).max(Integer::compareTo).get());
        PrintUtil.log(Stream.iterate(0, i -> i + 1).limit(10).reduce(Integer::max).get());
        PrintUtil.log(Stream.iterate(0, i -> i + 1).limit(10).reduce((i, j) -> i > j ? i : j).get());

        // 偶数个数
        PrintUtil.log(Stream.iterate(0, i -> i + 1).limit(10).filter(i -> i % 2 == 0).count());

        // 求和
        PrintUtil.log(Stream.iterate(0, i -> i + 1).limit(10).reduce((i, j) -> i + j).get());
        PrintUtil.log(Stream.iterate(0, i -> i + 1).limit(10).reduce(Integer::sum).get());

        // 乘积 返回Optional，初始identity为null
        PrintUtil.log(Stream.iterate(1, i -> i + 1).limit(10).reduce((i, j) -> i * j).get());
        // 返回U，初始identity为9999
        PrintUtil.log(Stream.iterate(1, i -> i + 1).limit(10).parallel().reduce(9999, (i, j) -> i * j));

        // parallel的时候会用到第三个参数combiner，用于合并多个线程产生的多个结果
        // 没传的话默认使用第二个参数替代
        // combiner需要与accumulator兼容
        // identity需要满足以下条件
        // combiner.apply(u, accumulator.apply(identity, t)) == accumulator.apply(u, t)
        int sum = Stream.iterate(1, i -> i + 1).limit(5).parallel().reduce(0, (i, j) -> {
            PrintUtil.log("i :{}, j :{}", i, j);
            return i + j;
        }, (i, j) -> {
            PrintUtil.log("a :{}, b :{}", i, j);
            return i + j;
        });
        PrintUtil.log("sum :{}", sum);

        // toMap 前提是key不重复，不然会报IllegalStateException : Duplicate key
        Stream.iterate(1, i -> i + 1).limit(5).collect(Collectors.toMap(i -> i, i -> i * 3)).forEach((k, v) -> PrintUtil.log("k :{}, v :{}", k, v));

        // 分组 奇偶分
        Map<Boolean, List<Integer>> partitionMap = Stream.iterate(1, i -> i + 1).limit(10).collect(Collectors.partitioningBy(i -> i % 2 == 0));

        partitionMap.forEach((k, v) -> PrintUtil.log("k :{}, v :{}", k, v));

        // 分组 性别
        Map<Integer, List<Person>> sexMap = list.stream().collect(Collectors.groupingBy(Person::getSex));
        sexMap.forEach((k, v) -> PrintUtil.log("k :{}, v :{}", k, v));

        // 分组 姓，性别 先按姓再按性别
        Map<String, Map<Integer, List<Person>>> surnameSexMap = list.stream().collect(Collectors.groupingBy(p -> p.getName().substring(0, 1), Collectors.groupingBy(Person::getSex)));
        surnameSexMap.forEach((k, v) -> PrintUtil.log("k :{}, v :{}", k, v));

        // join
        PrintUtil.log(list.stream().map(Person::getName).collect(Collectors.joining()));
        PrintUtil.log(list.stream().map(Person::getName).collect(Collectors.joining("`")));

        // flatMap
        String[] strArr = {"Hello", "World"};
        Arrays.stream(strArr).map(s -> s.split("")).flatMap(Arrays::stream).forEach(PrintUtil::log);

        // 排序
        list.stream().sorted(Comparator.comparingInt(Person::getSex).thenComparingInt(Person::getAge).reversed()).forEach(PrintUtil::log);

        // 流合并 去重
        Stream.concat(Stream.of('a', 'b', 'c', 'd'), Stream.of('b', 'd', 'e', 'f')).distinct().forEach(PrintUtil::log);

        // skip n limit n 跳过前n个，取前n个
        Stream.of('a', 'b', 'c', 'd', 'e', 'f').skip(2).limit(2).forEach(PrintUtil::log);

        // peek 中间操作下
        list.stream().peek(p -> p.setName("草")).forEach(PrintUtil::log);

        List<String> ll = Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(PrintUtil::log)
                .map(String::toUpperCase)
                .peek(PrintUtil::log)
                // 跳过n个元素
                .skip(1)
                .collect(Collectors.toList());

        PrintUtil.log();
        ll.forEach(PrintUtil::log);
    }

    public static boolean trueMethod(int i) {
        PrintUtil.log("" + i + true);
        return true;
    }

    public static boolean falseMethod(int i) {
        PrintUtil.log("" + i + false);
        return false;
    }
}
