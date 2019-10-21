package com.clei.Y2019.M09.D19;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.JWTUtil;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

public class JsonWebTokenTest {
    public static void main(String[] args) {
        Map<String,String> user = new HashMap<>();
        user.put("username","zhangsan");
        user.put("password","hasaki");
        String token = JWTUtil.createToken(user);
        System.out.println("token : " + token);
        Claims claims = JWTUtil.parseToken(token);
        Map<String,String> obj = claims.get("obj",Map.class);
        System.out.println(JSONObject.toJSONString(obj));
    }
}
