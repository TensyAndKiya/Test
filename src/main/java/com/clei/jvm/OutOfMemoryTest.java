package com.clei.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * OutOfMemoryError test
 *
 * @author KIyA
 */
public class OutOfMemoryTest {

    private final static String A = "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊";

    public static void main(String[] args) throws Exception {
        // heapSpace();
        directMemory();
    }

    private static void heapSpace() {
        List<String> list = new ArrayList<>();
        while (true) {
            list.add(A);
        }
    }

    /*private static void metaSpace() {
        int i = 0;
        while (true) {
            String str = A + i;
            str.intern();
        }
    }*/

    private static void directMemory() throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(1024 * 1024);
        }
    }
}
