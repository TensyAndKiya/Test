package com.clei.Y2019.M09.D20;

import com.clei.utils.PrintUtil;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * @author KIyA
 */
public class ForEachTest {

    public static void main(String[] args) {
        IterableClass<String> list = new IterableClass<>();
        list.add("hasaki");
        list.add("kisaha");
        list.add("sahaki");
        // forEach语法
        for (String s : list) {
            PrintUtil.dateLine(s);
        }
        // 集合forEach方法
        list.forEach(PrintUtil::dateLine);
    }


    /**
     * 实现Iterable
     *
     * @param <T>
     */
    private static class IterableClass<T> implements Iterable<T> {

        private final LinkedList<T> list = new LinkedList<>();

        public boolean add(T t) {
            return list.add(t);
        }

        public T get(int i) {
            return list.get(i);
        }

        @Override
        public void forEach(Consumer<? super T> action) {
            PrintUtil.dateLine("forEach");
            list.forEach(action);
        }

        @Nonnull
        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private int i;

                @Override
                public boolean hasNext() {
                    return list.size() > 0 && i < list.size();
                }

                @Override
                public T next() {
                    return list.get(i++);
                }
            };
        }
    }
}
