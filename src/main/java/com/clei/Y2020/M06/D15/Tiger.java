package com.clei.Y2020.M06.D15;

/**
 * Tiger bean
 *
 * @author KIyA
 */
public class Tiger {

    private String name = "小老虎";

    private int age = 18;

    private int color = 1;

    public Tiger() {
    }

    public Tiger(String name, int age, int color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Tiger{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color=" + color +
                '}';
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
