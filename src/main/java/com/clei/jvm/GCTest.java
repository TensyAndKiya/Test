package com.clei.jvm;

import com.clei.utils.PrintUtil;
import com.clei.utils.SystemUtil;

/**
 * GC test
 *
 * @author KIyA
 */
public class GCTest {

    public static void main(String[] args) {
        int oneM = 1024 * 1024;
        PrintUtil.log(oneM);
        /**
         * jvm参数 -Xmx20M -Xms20M -Xmn10M -XX:+PrintGCDetails
         *
         * 可选 -XX:UseConcMarkSweepGC -XX:PretenureSizeThreshold=3145728
         * 使用-XX:UseConcMarkSweepGC 是因为默认使用的Parallel Scavenge + Parallel Old
         * 而-XX:PretenureSizeThreshold对Scavenge不起作用，其只对Serial和ParNew有效
         */

        byte[] data1 = new byte[2 * oneM];
        byte[] data2 = new byte[2 * oneM];
        byte[] data3 = new byte[2 * oneM];
        byte[] data4 = new byte[4 * oneM];

        SystemUtil.pause();
    }

}
