package com.clei.Y2020.M09.D03;

/**
 * HashMap初始化容量
 * @author KIyA
 */
public class TableSizeForTest {

    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    public static void main(String[] args) {

        System.out.println(Math.pow(2,14 - 14));
        System.out.println(Math.pow(2,14 - 19));
        System.out.println(Math.pow(2,14 - 14));
        System.out.println(Math.pow(2,14 - 17));
        System.out.println(Math.pow(2,14 - 16));
        System.out.println(Math.pow(2,14 - 15));
        System.out.println(Math.pow(2,14 - 1));

        int capacity = 7;

        System.out.println(tableSizeFor(7));
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        System.out.println(cap);
        int n = cap - 1;
        System.out.println(n);
        n |= n >>> 1;
        System.out.println(n);
        n |= n >>> 2;
        System.out.println(n);
        n |= n >>> 4;
        System.out.println(n);
        n |= n >>> 8;
        System.out.println(n);
        n |= n >>> 16;
        System.out.println(n);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }



}
