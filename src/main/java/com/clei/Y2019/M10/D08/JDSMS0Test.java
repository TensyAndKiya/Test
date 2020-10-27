package com.clei.Y2019.M10.D08;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.MD5Util;
import com.clei.utils.OkHttpUtil;
import com.clei.utils.PrintUtil;

public class JDSMS0Test {
    private final static String account = "aaa";
    private final static String password = "bbb";
    private final static String url = "http://116.196.117.211:5555/jdPayResult";
    public static void main(String[] args) {
        JSONObject message = new JSONObject();
        message.put("password",password);
        message.put("account",account);
        message.put("ukey", MD5Util.md5("" + Math.random()));
        JSONObject messageData = new JSONObject();
        messageData.put("phones", "184XXXXXXXX");
        messageData.put("content", "赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。");
        messageData.put("sign","【客户签名1】");
        message.put("data",messageData);
        String result = OkHttpUtil.doPost(url,message.toJSONString());
        PrintUtil.dateLine("result : " + result);
    }
}
