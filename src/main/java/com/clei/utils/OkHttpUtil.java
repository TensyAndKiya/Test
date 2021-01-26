package com.clei.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

/**
 * OkHttp 工具类
 *
 * @author KIyA
 */
public class OkHttpUtil {

    /**
     * post json
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPostJson(String url, String param) {
        PrintUtil.dateLine("请求url:{},请求参数:{}", url, param);
        return doRequest(url, getJsonBody(param));
    }

    /**
     * post json
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPostJson(String url, Object param) {
        String json = JSONObject.toJSONString(param);
        PrintUtil.dateLine("请求url:{},请求参数:{}", url, json);
        return doRequest(url, getJsonBody(json));
    }

    /**
     * post form
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPostForm(String url, Map<String, Object> param) {
        return doRequest(url, getFormBody(param));
    }

    /**
     * post form
     *
     * @param url
     * @param field
     * @param value
     * @return
     */
    public static String doPostForm(String url, String field, Object value) {
        return doRequest(url, getFormBody(field, value));
    }

    /**
     * Get 请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doRequest(url, null);
    }

    /**
     * Get 请求
     *
     * @param url
     * @param charset 指定返回字符串的字符集
     * @return
     */
    public static String doGet(String url, Charset charset) {
        return doRequest(url, null, charset);
    }

    /**
     * Get 请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url, String field, Object value) {
        String result = null;
        try {
            result = doRequest(getUrl(url, field, value), null);
        } catch (UnsupportedEncodingException e) {
            PrintUtil.log("请求[{}]拼接参数时出错，参数值：{}", url, value);
        }
        return result;
    }

    /**
     * Get 请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url, Map<String, Object> param) {
        String result = null;
        try {
            result = doRequest(getUrl(url, param), null);
        } catch (UnsupportedEncodingException e) {
            PrintUtil.log("请求[{}]拼接参数时出错，参数值：{}", url, param);
        }
        return result;
    }

    /**
     * get url
     *
     * @param url
     * @param field
     * @param value
     * @return
     */
    private static String getUrl(String url, String field, Object value) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(url);
        sb.append('?');
        sb.append(field);
        sb.append(URLEncoder.encode(String.valueOf(value), "UTF-8"));
        return sb.toString();
    }

    /**
     * get url
     *
     * @param url
     * @param param
     * @return
     */
    private static String getUrl(String url, Map<String, Object> param) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(url);
        sb.append('?');
        Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            sb.append(next.getKey());
            sb.append('=');
            sb.append(URLEncoder.encode(String.valueOf(next.getValue()), "UTF-8"));
            sb.append('&');
        }
        if ('&' == sb.charAt(sb.length() - 1)) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * json body
     *
     * @param param
     * @return
     */
    private static RequestBody getJsonBody(String param) {
        return RequestBody.create(param, MediaType.parse("application/json; charset=utf-8"));
    }

    /**
     * form body
     *
     * @param field
     * @param value
     * @return
     */
    private static RequestBody getFormBody(String field, Object value) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add(field, String.valueOf(value));
        return builder.build();
    }

    /**
     * form body
     *
     * @param param
     * @return
     */
    private static RequestBody getFormBody(Map<String, Object> param) {
        FormBody.Builder builder = new FormBody.Builder();
        param.forEach((k, v) -> builder.add(k, String.valueOf(v)));
        return builder.build();
    }

    /**
     * multipart body
     *
     * @param file
     * @return
     */
    private static RequestBody getFormBody(File file) {
        return RequestBody.create(file, MediaType.parse("application/octet-stream"));
    }

    /**
     * multipart body
     *
     * @param map
     * @param param
     * @return
     */
    private static RequestBody getMultipartBody(Map<String, File> map, Map<String, Object> param) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        map.forEach((k, v) -> builder.addFormDataPart(k, v.getName(), getFormBody(v)));
        builder.addPart(getFormBody(param));
        return builder.build();
    }

    /**
     * Get / Post 请求
     *
     * @param url
     * @param requestBody
     * @param charsets    指定返回字符串使用的字符集
     * @return
     */
    private static String doRequest(String url, RequestBody requestBody, Charset... charsets) {
        Request.Builder builder = new Request.Builder().url(url);
        // null GET
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
            PrintUtil.log("请求[{}]出错", url, e);
        }
        // 响应判断
        if (null != response) {
            if (response.isSuccessful()) {
                String result = null;
                ResponseBody body = response.body();
                try {
                    if (charsets.length > 0) {
                        byte[] bytes = body.bytes();
                        result = new String(bytes, charsets[0]);
                    } else {
                        result = body.string();
                    }
                } catch (IOException e) {
                    PrintUtil.log("", e);
                }
                PrintUtil.log("请求[{}]成功，结果：{}", url, result);
                return result;
            } else {
                PrintUtil.log("请求[{}]失败，code：{}", url, response.code());
                return null;
            }
        }
        PrintUtil.log("请求[{}]无响应", url);
        return null;
    }

}
