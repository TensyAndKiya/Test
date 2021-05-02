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

        PrintUtil.log(Math.pow(2, 14 - 14));
        PrintUtil.log(Math.pow(2, 14 - 19));
        PrintUtil.log(Math.pow(2, 14 - 18));
        PrintUtil.log(Math.pow(2, 14 - 17));
        PrintUtil.log(Math.pow(2, 14 - 16));
        PrintUtil.log(Math.pow(2, 14 - 15));
        PrintUtil.log(Math.pow(2, 14 - 1));

        int capacity = 7;

        PrintUtil.log(tableSizeFor(capacity));
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        PrintUtil.log(cap);
        int n = cap - 1;
        PrintUtil.log(n);
        n |= n >>> 1;
        PrintUtil.log(n);
        n |= n >>> 2;
        PrintUtil.log(n);
        n |= n >>> 4;
        PrintUtil.log(n);
        n |= n >>> 8;
        PrintUtil.log(n);
        n |= n >>> 16;
        PrintUtil.log(n);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
