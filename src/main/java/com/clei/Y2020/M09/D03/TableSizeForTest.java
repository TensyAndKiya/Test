package com.clei.Y2020.M09.D03;

import com.clei.utils.PrintUtil;

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

        PrintUtil.dateLine(Math.pow(2, 14 - 14));
        PrintUtil.dateLine(Math.pow(2, 14 - 19));
        PrintUtil.dateLine(Math.pow(2, 14 - 14));
        PrintUtil.dateLine(Math.pow(2, 14 - 17));
        PrintUtil.dateLine(Math.pow(2, 14 - 16));
        PrintUtil.dateLine(Math.pow(2, 14 - 15));
        PrintUtil.dateLine(Math.pow(2, 14 - 1));

        int capacity = 7;

        PrintUtil.dateLine(tableSizeFor(7));
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        PrintUtil.dateLine(cap);
        int n = cap - 1;
        PrintUtil.dateLine(n);
        n |= n >>> 1;
        PrintUtil.dateLine(n);
        n |= n >>> 2;
        PrintUtil.dateLine(n);
        n |= n >>> 4;
        PrintUtil.dateLine(n);
        n |= n >>> 8;
        PrintUtil.dateLine(n);
        n |= n >>> 16;
        PrintUtil.dateLine(n);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }



}
