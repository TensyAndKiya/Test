package com.clei.Y2020.M07.D10;

import com.clei.utils.EncryptUtil;
import com.clei.utils.MD5Util;

import java.util.TreeMap;

public class SignTest {

    public static void main(String[] args) throws Exception {

        //1505785966215
        System.out.println(System.currentTimeMillis());
        System.out.println(1505785966215L);

        String str = "中文";

        String md51 = EncryptUtil.md5(str);

        String md52 = MD5Util.md5(str);

        String md53 = MD5Util.md5Str(str,0);

        System.out.println(md51);

        System.out.println(md52);

        System.out.println(md53);

        System.out.println(EncryptUtil.sha1(md51));


        TreeMap<String,Object> param = new TreeMap<>();
        // 接口名
        param.put("method","method");
        // 给开发者颁发的 key
        param.put("appKey","appKey");
        // token 用于调用接口前授权检
        param.put("token","token");
        // Unix时间戳
        param.put("timestamp","timestamp");
        // 响应格式。默认为json格式，可选值：xml，json。
        param.put("format","json");
        // API协议版本
        param.put("version","3.0");
        // 请求类型，默认 sync，可选值asyn，sync。
        param.put("type","sync");

        // 公共参数拼接字符串
        StringBuilder sb = new StringBuilder();
        param.forEach((k,v) -> {
            sb.append(k);
            sb.append('=');
            sb.append(v);
            sb.append('&');
        });
        String publicParamStr = sb.deleteCharAt(sb.length() - 1).toString();

        String appSecret = "appsecret";

        String signStr = appSecret + publicParamStr + "中文汉字哦" + appSecret;

        System.out.println(signStr);

        String sign = EncryptUtil.md5(signStr,"UTF-8",true);

        System.out.println(EncryptUtil.md5(signStr));
        System.out.println(EncryptUtil.md5(signStr,"UTF8",false));

        System.out.println(sign);
    }

}
