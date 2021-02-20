package com.clei.Y2021.M02.D19;

import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串拼接类StringJoiner试用
 *
 * @author KIyA
 */
public class StringJoinerTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(3);
        list.add("张三");
        list.add("李四");
        list.add("王五");
        PrintUtil.log(StringUtil.join(list, "()", ",", "(", ")"));
        PrintUtil.log(StringUtil.strJoin(list, "()", ",", "(", ")"));
    }
}
