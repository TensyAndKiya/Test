package com.clei.Y2019.M06.D29;

import com.clei.entity.Person;
import com.clei.utils.PrintUtil;

import java.util.Optional;

/**
 * Optional 常用来封装一些可能为null的对象
 *
 * @author KIyA
 */
public class Java8Test6 {

    public static void main(String[] args) {
        Person person = new Person("阿三", 18, 1);

        // 创建一个空的
        Optional<Person> optional1 = Optional.empty();
        // 如果person == null的话，会立即抛出NullPointerException,而不是等你用到person的时候
        Optional<Person> optional2 = Optional.of(person);
        // 创建一个允许null值的
        Optional<Person> optional3 = Optional.ofNullable(person);
        // 创建个String的
        Optional<String> strOptional = optional2.map(Person::getName);

        PrintUtil.println("存在？{}", optional2.isPresent());

        // Person p = optional2.get();
        Person p = optional2.orElse(new Person("阿四", 19, 0));
        //  Person p2 = optional2.orElseGet()
    }
}
