package com.clei.Y2019.M09.D20;

import com.clei.utils.PrintUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

public class ForEachTest {
    public static void main(String[] args) {
        KS<String> list = new KS();
        list.add("hasaki");
        list.add("kisaha");
        list.add("sahaki");
        // forEach语法
        for(String s : list){
            PrintUtil.dateLine(s);
        }
        // 集合forEach方法
        list.forEach(s -> PrintUtil.dateLine(s));
    }


    // 实现Iterable
    private static class KS<T> implements Iterable<T>{
        private final LinkedList<T> list = new LinkedList();

        public boolean add(T t){
            return list.add(t);
        }

        public T get(int i){
            return list.get(i);
        }

        @Override
        public void forEach(Consumer action) {
            PrintUtil.dateLine("forEach");
            for(T t : list){
                action.accept(t);
            }
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private int i;
                @Override
                public boolean hasNext() {
                    if(list.size() > 0 && i < list.size()){
                        return true;
                    }
                    return false;
                }

                @Override
                public T next() {
                    return list.get(i++);
                }
            };
        }
    }
}
