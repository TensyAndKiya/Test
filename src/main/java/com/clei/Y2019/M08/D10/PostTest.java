package com.clei.Y2019.M08.D10;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.PrintUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class PostTest {
    public static void main(String[] args) throws IOException {

        JSONObject entity = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("license", "aaa");
        content.put("parkId", "bbbb");
        entity.put("condition", content);
        PrintUtil.dateLine(content.toJSONString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), entity.toJSONString());
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                // .addFormDataPart("condition",content.toJSONString())
                .addPart(requestBody)
                .build();
        PrintUtil.dateLine("contentType : " + body.contentType());
        PrintUtil.dateLine("type : " + body.type());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://114.55.170.245:8099/park/rs/cloudRequest/cloudEnReq")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            PrintUtil.dateLine(response.body().string());
        } else {
            PrintUtil.dateLine("fail!!!");
        }
        PrintUtil.dateLine(response.code());
    }
}
