package com.clei.Y2019.M08.D10;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;

public class PostTest {
    public static void main(String[] args) throws IOException {

        JSONObject entity = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("license","aaa");
        content.put("parkId","bbbb");
        entity.put("condition",content);
        System.out.println(content.toJSONString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), entity.toJSONString());
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                // .addFormDataPart("condition",content.toJSONString())
                .addPart(requestBody)
                .build();
        System.out.println("contentType : " + body.contentType());
        System.out.println("type : " + body.type());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://114.55.170.245:8099/park/rs/cloudRequest/cloudEnReq")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()){
            System.out.println(response.body().string());
        }else{
            System.out.println("fail!!!");
        }
        System.out.println(response.code());
    }
}
