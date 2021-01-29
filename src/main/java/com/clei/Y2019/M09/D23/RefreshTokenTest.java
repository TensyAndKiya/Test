package com.clei.Y2019.M09.D23;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.OkHttpUtil;
import com.clei.utils.PrintUtil;


public class RefreshTokenTest {
    public static void main(String[] args) throws Exception {
        refreshAccessToken();
    }

    private static void refreshAccessToken() throws Exception {
        String url = "http://open.hydzfp.com/open_access/access/token.open";
        JSONObject obj = new JSONObject();
        obj.put("openid", "Ck0MxKBl5It1ln2wHqoVRRYwyIu35KZrQ09OaqhCaZDRNFr31751555312185047");
        obj.put("app_secret", "sAVCABbDO7e2cv9U60NekjfW6wd9GFtlphvV9u4DaE9kljzWH6P1555312185049");

        String result = OkHttpUtil.doPostJson(url, obj.toJSONString());
        PrintUtil.log(result);
    }
}
