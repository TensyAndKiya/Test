package com.clei.Y2019.M04.D08;

import java.util.Calendar;
import java.util.Date;

public class FinalTest {
    public static void main(String[] args) {
        //function(new Dog());
        System.out.println(new Date().getTime());
        System.out.println(System.currentTimeMillis());
        System.out.println(Calendar.getInstance().getTimeInMillis());
    }

    private static void function(final Dog dog){
        System.out.println(dog);
        // dog = new Dog();
        dog.setAge(998);
        System.out.println(dog);
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
