package com.clei.Y2019.M09.D19;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.JWTUtil;
import com.clei.utils.PrintUtil;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JSON WEB TOKEN 测试
 *
 * @author KIyA
 */
public class JsonWebTokenTest {

    public static void main(String[] args) {
        Map<String, String> user = new HashMap<>(4);
        user.put("username", "zhangsan");
        user.put("password", "hasaki");
        String token = JWTUtil.createToken(user);
        PrintUtil.log("token : {}", token);
        Claims claims = JWTUtil.parseToken(token);
        PrintUtil.log("claims : {}", JSONObject.toJSONString(claims));
        PrintUtil.log("obj : {}", JSONObject.toJSONString(claims.get("obj", LinkedHashMap.class)));
    }
}
