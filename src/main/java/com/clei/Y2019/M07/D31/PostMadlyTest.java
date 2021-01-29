package com.clei.Y2019.M07.D31;

import com.clei.utils.PrintUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class PostMadlyTest {
    private static int j;
    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 50; i++) {
            j ++;
            doPost();
            // Thread.sleep((long)(Math.random()*30) + 5);
        }
    }

    private static void doPost() throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("parkCode","bb");
        builder.add("vplNumber","bb");
        builder.add("recordId","cc" + j);
        builder.add("billId","ee");
        builder.add("payTime","1000");
        builder.add("payList","[{}]");
        Request request = new Request.Builder()
                .url("http://111.111.111.111:1111/park/cmbParking/pay")
                .post(builder.build())
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        if(null != response && response.isSuccessful()){
            String result = response.body().string();
            PrintUtil.log("result: " + result);
        }
        if(null != response){
            response.close();
        }
    }
}
