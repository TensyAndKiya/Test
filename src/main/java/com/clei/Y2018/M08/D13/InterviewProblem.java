package com.clei.Y2018.M08.D13;

import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 虽然类名为InterviewProblem 面试遇到的问题
 * 但是并没有说明遇到了什么问题
 * 想要验证什么东西
 * 这样不好
 * <p>
 * 两年多过去了
 * 从代码来看的话
 * 应该是想验证map的remove和put的返回
 * 以及集合排序通过Comparable和Comparator实现
 *
 * @author KIyA
 */
public class InterviewProblem {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "key1");
        map.put("key2", "key2");
        PrintUtil.log(map.get("key1"));
        PrintUtil.log(map.remove("key1"));
        PrintUtil.log(map.remove("key3"));
        PrintUtil.log(map.put("key1", "new key1"));
        PrintUtil.log(map.put("key2", "new key2"));

        //
        User u1 = new User(5, "张三");
        User u2 = new User(4, "李四");
        User u3 = new User(3, "王五");
        List<User> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        list.add(u3);
        PrintUtil.log(list);
        //list.sort(new UserComparator());
        //Collections.sort(list,new UserComparator());
        Collections.sort(list);
        PrintUtil.log(list);
        Collections.reverse(list);
        PrintUtil.log(list);
    }
}

class User implements Comparable<User> {

    private int age;
    private String name;

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public int compareTo(User u) {
        int result = -1;
        if (this.getAge() > u.getAge()) {
            result = 1;
        } else if (this.getAge() == u.getAge()) {
            result = this.getName().compareTo(u.getName());
        }
        return result;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

class UserComparator implements Comparator<User> {

    @Override
    public int compare(User u1, User u2) {
        int result = -1;
        if (u1.getAge() > u2.getAge()) {
            result = 1;
        } else if (u1.getAge() == u2.getAge()) {
            result = u1.getName().compareTo(u2.getName());
        }
        return result;
    }
}
