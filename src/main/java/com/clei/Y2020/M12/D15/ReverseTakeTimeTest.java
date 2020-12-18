package com.clei.Y2020.M12.D15;

import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;
import org.springframework.util.StopWatch;

/**
 * reverse耗时
 *
 * @author KIyA
 */
public class ReverseTakeTimeTest {

    public static void main(String[] args) {
        StopWatch watch = new StopWatch();
        // 循环次数
        int times = 100000000;
        // 反转的字符串
        String str = "012345678910";
        // 通过自定义reverse方法
        watch.start();
        for (int i = 0; i < times; i++) {
            StringUtil.reverse(str);
        }
        watch.stop();
        PrintUtil.log("耗时：{}ms", watch.getLastTaskTimeMillis());
        // 通过StringBuilder reverse
        watch.start();
        for (int i = 0; i < times; i++) {
            new StringBuilder(str).reverse().toString();
        }
        watch.stop();
        PrintUtil.log("耗时：{}ms", watch.getLastTaskTimeMillis());
    }
}
