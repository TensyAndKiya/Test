package com.clei.Y2019.M07.D31;

import com.clei.utils.OkHttpUtil;
import com.clei.utils.PrintUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 疯狂地请求某个接口
 *
 * @author KIyA
 */
public class PostMadlyTest {

    public static void main(String[] args) {
        String url = "http://111.111.111.111:1111/park/cmbParking/pay";
        int requestTimes = 50;
        for (int i = 0; i < requestTimes; i++) {
            doPost(url, i);
        }
    }

    /**
     * post url
     *
     * @param url url
     * @param i   序号
     */
    private static void doPost(String url, int i) {
        Map<String, Object> param = new HashMap<>(6);
        param.put("parkCode", "bb");
        param.put("vplNumber", "bb");
        param.put("recordId", "cc" + i);
        param.put("billId", "ee");
        param.put("payTime", "1000");
        param.put("payList", "[{}]");
        String result = OkHttpUtil.doPostForm(url, param);
        PrintUtil.log("result: {}", result);
    }
}
