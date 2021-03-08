package com.clei.temp;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 临时的小段代码的测试2
 *
 * @author KIyA
 */
public class TempTest2 {

    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(3);
        list2.add(4);
        // 交集
        list1.retainAll(list2);
        PrintUtil.log(list1);
        //
        PrintUtil.log(DateUtil.formatMillis(1615172083862L));

        PrintUtil.log(DateUtil.toEpochSecond("2021-03-05 18:08:08"));
        PrintUtil.log(DateUtil.toEpochMilli("2021-03-05 18:08:08"));
    }
}
