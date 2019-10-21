package com.clei.Y2019.M10.D08;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.PrintUtil;
import com.jdcloud.sdk.auth.CredentialsProvider;
import com.jdcloud.sdk.auth.StaticCredentialsProvider;
import com.jdcloud.sdk.http.HttpRequestConfig;
import com.jdcloud.sdk.http.Protocol;
import com.jdcloud.sdk.service.sms.client.SmsClient;
import com.jdcloud.sdk.service.sms.model.BatchSendRequest;
import com.jdcloud.sdk.service.sms.model.BatchSendResponse;

import java.util.Arrays;
import java.util.List;

public class JDSMSTest {
    public static void main(String[] args) {
        String accessKeyId = "";
        String secretAccessKey = "";
        List<String> phoneList = Arrays.asList("18408244077");
        List<String> params = Arrays.asList("4077");

        CredentialsProvider credentialsProvider = new StaticCredentialsProvider(accessKeyId,secretAccessKey);
        HttpRequestConfig requestConfig = new HttpRequestConfig.Builder()
                .protocol(Protocol.HTTPS).build();
        SmsClient smsClient = SmsClient.builder()
                .credentialsProvider(credentialsProvider)
                .httpRequestConfig(requestConfig)
                .build();
        BatchSendRequest request = new BatchSendRequest();
        // Region ID
        request.setRegionId("cn-north-1");
        // 短信模版ID
        request.setTemplateId("");
        // 签名ID
        request.setSignId("");
        // 号码些
        request.setPhoneList(phoneList);
        // 参数些
        request.setParams(params);
        // 发送
        BatchSendResponse response = smsClient.batchSend(request);
        System.out.println("env : " + JSONObject.toJSONString(smsClient.getEnvironment()));
        if(null != response){
            PrintUtil.println("京东云 文本短信 群发 结果 requestId:{},result:{}",response.getRequestId(), JSONObject.toJSONString(response.getResult()));
        }else {
            PrintUtil.println("京东云 文本短信 群发 响应为null");
        }
    }
}
