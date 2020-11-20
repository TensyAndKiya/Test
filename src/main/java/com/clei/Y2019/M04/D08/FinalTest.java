package com.clei.Y2019.M04.D08;

import com.clei.utils.PrintUtil;

import java.util.Calendar;
import java.util.Date;

public class FinalTest {
    public static void main(String[] args) {
        //function(new Dog());
        PrintUtil.dateLine(new Date().getTime());
        PrintUtil.dateLine(System.currentTimeMillis());
        PrintUtil.dateLine(Calendar.getInstance().getTimeInMillis());
    }

    private static void function(final Dog dog){
        PrintUtil.dateLine(dog);
        // dog = new Dog();
        dog.setAge(998);
        PrintUtil.dateLine(dog);
    }

    static class Dog{
        private String name;
        private int age;

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

        @Override
        public String toString() {
            return "Dog{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
