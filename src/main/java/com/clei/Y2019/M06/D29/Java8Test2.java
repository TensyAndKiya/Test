package com.clei.Y2019.M06.D29;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.clei.utils.PrintUtil.println;

public class Java8Test2 {
    public static void main(String[] args) {
        List<Car> list = new ArrayList<>();
        // 构造器引用 Class::new
        list.add(Car.create(Car::new));
        list.add(new Car("老狗"));
        list.add(new Car("老猫"));

        // 静态方法引用 Class::static_method
        list.forEach(Car::collide);
        // 特定类的任意对象的方法引用 Class::method
        list.forEach(Car::repair);
        // 特定对象的方法引用 instance::method
        Car car = new Car("挖掘机");
        list.forEach(car::follow);
    }
}

class Car {
    private String name;

    public Car() {
        this("老母猪！");
    }

    public Car(String name) {
        this.name = name;
    }


    public static Car create(Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(Car car) {
        println("collide {}", car.toString());
    }

    public void follow(Car car) {
        println("{} is following the {}", this.toString(), car.toString());
    }

    public void repair() {
        println("repaired {}", this.toString());
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                '}';
    }
}
