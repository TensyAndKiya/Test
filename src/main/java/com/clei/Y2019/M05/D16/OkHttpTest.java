package com.clei.Y2019.M05.D16;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 疯狂的去访问计算器
 * 看看能不能重现错误
 */
public class OkHttpTest {
    private final static String START_TIME = "2019-05-01 00:00:00";
    private final static String END_TIME = "2019-05-16 23:23:23";
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) throws Exception{
        long startMills = SDF.parse(START_TIME).getTime();
        long endMills = SDF.parse(END_TIME).getTime();
        String startTime = START_TIME;
        do{
            doPost(startTime,END_TIME);
            //加1秒
            startMills += 1000;
            startTime = SDF.format(new Date(startMills));
            //休息一下，不然遭不住
            Thread.sleep(10);
        } while(startMills < endMills);
    }

    private static void doPost(String startTime,String endTime) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("beginTime",startTime);
        builder.add("endTime",endTime);
        builder.add("colorType","0");
        builder.add("sizeType","10");
        FormBody formBody = builder.build();
        System.out.println(formBody.contentType());
        Request request = new Request.Builder()
                .url("http://localhost:8080/park/sentrybox/calculator/calculate?JSESSIONID=11299d11-4612-4676-adc0-b7a9bf67b995")
                .post(formBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        if(null != response && response.isSuccessful()){
            String result = response.body().string();
            System.out.println("result: " + result);
        }
        if(null != response){
            response.close();
        }
    }
}
