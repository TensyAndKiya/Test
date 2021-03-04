package com.clei.entity;

/**
 * 常用到的一些简单类 人
 *
 * @author
 */
public class Person implements Comparable<Person> {

    private String name;
    private int age;
    private int sex;

    public Person(String name, int age, int sex) {
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

    @Override
    public int compareTo(Person o) {
        int compare = this.name.compareTo(o.getName());
        if (0 == compare) {
            compare = this.sex - o.getSex();
            if (0 == compare) {
                return Integer.compare(this.age, o.getAge());
            }
        } else {
            return compare;
        }
        return compare;
    }
}
