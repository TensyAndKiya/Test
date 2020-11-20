package com.clei.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class OkHttpUtil {

    public static String doPost(String url, String param) {
        PrintUtil.dateLine("请求url:{},请求参数:{}", url, param);
        return doPostRequest(url, getBody(param));
    }

    public static String doPost(String url, Map<String, String> param) {
        PrintUtil.dateLine("请求url:{},请求参数:{}", url, JSONObject.toJSONString(param));
        return doPostRequest(url, getBody(param));
    }

    public static String doPost(String url, File file, Object object) {
        return null;
    }

    public static RequestBody getBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        return requestBody;
    }

    private static RequestBody getBody(Map<String, String> param) {
        FormBody.Builder builder = new FormBody.Builder();
        param.forEach((k, v) -> builder.add(k, v));
        return builder.build();
    }

    private static RequestBody getBody(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        new MultipartBody.Builder().build();
        return requestBody;
    }

    private static RequestBody getBody(Map<String, File> map, Map<String, String>... param) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        map.forEach((k, v) -> builder.addFormDataPart(k, v.getName(), getBody(v)));
        for (Map<String, String> p : param) {
            builder.addPart(getBody(p));
        }
        return null;
    }

    /**
     * Get 请求
     *
     * @param url
     * @return
     */
    public static String doGetRequest(String url) {
        return doRequest(url, null);
    }

    /**
     * Post 请求
     *
     * @param url
     * @param requestBody
     * @return
     */
    private static String doPostRequest(String url, RequestBody requestBody) {
        return doRequest(url, requestBody);
    }

    /**
     * Get / Post 请求
     *
     * @param url
     * @param requestBody
     * @return
     */
    private static String doRequest(String url, RequestBody requestBody) {
        Request.Builder builder = new Request.Builder().url(url);
        if (null == requestBody) {
            builder.get();
        } else {
            builder.post(requestBody);
        }
        Request request = builder.build();
        Response response = null;
        try {
            response = new OkHttpClient().newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != response) {
            if (response.isSuccessful()) {
                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PrintUtil.dateLine("请求结果： " + result);
                return result;
            } else {
                PrintUtil.dateLine("API调用失败，Code:{}", response.code());
            }
        }
        return null;
    }

}
