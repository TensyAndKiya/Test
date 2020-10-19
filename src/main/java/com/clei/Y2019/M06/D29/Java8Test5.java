package com.clei.Y2019.M06.D29;

import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Java8Test5 {
    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        list.add(new Person("张三",18,1));
        list.add(new Person("李四",19,0));
        list.add(new Person("李端",22,0));
        list.add(new Person("王五",20,1));
        list.add(new Person("赵秦",25,0));
        list.add(new Person("张泽",21,1));
        list.add(new Person("王炸",23,1));
        list.add(new Person("陈某",24,1));

        // 找出姓王的
        List<Person> listWang = list.stream().filter( p -> p.getName().startsWith("王")).collect(Collectors.toList());
        listWang.forEach(System.out :: println);
        // 找出女人的数目
        long girlCount = list.stream().filter(v -> v.getSex() == 0).count();
        PrintUtil.dateLine(girlCount);

        // 排序
        PrintUtil.dateLine("原来顺序");
        list.forEach(System.out::println);
        PrintUtil.dateLine("排序后");
        list.sort(Comparator.comparingInt(Person::getAge).reversed());
        list.forEach(System.out :: println);

        // 所有人
        String everyone = list.stream().map(Person::getName).collect(Collectors.joining(","));
        PrintUtil.dateLine(everyone);
    }
}

class Person{
    private String name;
    private int age;
    private int sex;

    public Person(String name,int age,int sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + (sex == 1 ? "男" : "女") +
                '}';
    }
}
