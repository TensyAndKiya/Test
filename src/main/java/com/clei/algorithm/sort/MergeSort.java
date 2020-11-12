package com.clei.algorithm.sort;

import com.clei.utils.PrintUtil;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 归并排序
 *
 * @author KIyA
 */
public class MergeSort {

    public static void main(String[] args) {
        Date now = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        PrintUtil.println("now : " + sdf.format(now));

        PrintUtil.println("millis : " + now.getTime());

        now = DateUtils.setSeconds(now, 0);

        PrintUtil.println("now : " + sdf.format(now));

        PrintUtil.println("millis : " + now.getTime());

        // 舍弃秒 毫秒
        long time = now.getTime();
        long diff = time % 60000;
        time = time - diff;
        now = new Date(time);

        PrintUtil.println("now : " + sdf.format(now));

        PrintUtil.println("millis : " + now.getTime());
    }
}
