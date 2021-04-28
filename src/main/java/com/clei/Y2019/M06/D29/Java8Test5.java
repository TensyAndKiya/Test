package com.clei.Y2019.M06.D29;

import com.clei.entity.Person;
import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 学习Java8新特性
 *
 * @author KIyA
 */
public class Java8Test5 {

    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        list.add(new Person("张三", 18, 1));
        list.add(new Person("李四", 19, 0));
        list.add(new Person("李端", 22, 0));
        list.add(new Person("王五", 20, 1));
        list.add(new Person("赵秦", 25, 0));
        list.add(new Person("张泽", 21, 1));
        list.add(new Person("王炸", 23, 1));
        list.add(new Person("陈某", 24, 1));

        // 找出姓王的
        List<Person> listWang = list.stream().filter(p -> p.getName().startsWith("王")).collect(Collectors.toList());
        listWang.forEach(PrintUtil::println);
        // 找出女人的数目
        long girlCount = list.stream().filter(v -> v.getSex() == 0).count();
        PrintUtil.log(girlCount);

        // 排序
        PrintUtil.log("原来顺序");
        list.forEach(PrintUtil::println);
        PrintUtil.log("排序后");
        list.sort(Comparator.comparingInt(Person::getAge).reversed());
        list.forEach(PrintUtil::println);

        // 所有人
        String everyone = list.stream().map(Person::getName).collect(Collectors.joining(","));
        PrintUtil.log(everyone);
    }
}
