package com.clei.Y2019.M06.D29;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.clei.utils.PrintUtil.println;

public class Java8Test3 {
    public static void main(String[] args) {
        Random random = new Random();
        Integer[] ints = random.ints(0,101).limit(50).boxed().toArray(Integer[]::new);
        // 0,101 左闭右开
        List<Integer> list = random.ints(0,101).limit(50).boxed().collect(Collectors.toList());
        // v -> v % 3 == 0 就是个Predicate<T>实现
        eval(ints,v -> v % 3 == 0);
    }

    private static void eval(Integer[] ints, Predicate<Integer> predicate){
        if(null != ints && null != predicate){
            for(Integer i : ints){
                println("{} predicate : {}",i,predicate.test(i));
            }
            println("LENGTH:{}",ints.length);
        }
    }
}

