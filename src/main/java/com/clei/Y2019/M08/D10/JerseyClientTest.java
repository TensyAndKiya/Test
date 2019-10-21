package com.clei.Y2019.M08.D10;

import com.alibaba.fastjson.JSONObject;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.*;

public class JerseyClientTest {
    private static Client client = null;
    private static String parkId = "9952dbb8204e4ae892b8bd43d0e56dfe";
    private static String prefix;
    private static volatile int tps;
    private static int success = 0;
    private static int executed = 0;
    private static long start = System.currentTimeMillis();
    public static void main(String[] args) {
        if(args.length == 2){
            prefix = args[0];
            tps = Integer.parseInt(args[1]);
        }else{
            System.out.println("参数不对！！！");
        }
        doPost(tps);
    }

    private static void doPost(int tps){

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                4,
                9,
                1,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(10),
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler()
        );

        for (int i = 0; i < tps; i++) {
            MyRunnable runnable = new MyRunnable();
            executor.submit(runnable);
        }
    }

    private static String getCarLicense(int i){
        String str = "" + i;
        for (int j = 0; j < 6 - str.length(); j++) {
            str = '0' + str;
        }
        return prefix + str;
    }

    private static void doPostRequest(String content){
        WebTarget target = getClient().target("http://114.55.170.245:8099/park/rs/cloudRequest/cloudEnReq");
        MultiPart multipartEntity = new FormDataMultiPart().field("condition", content, MediaType.APPLICATION_JSON_TYPE);
        Response response = target.request().accept(MediaType.APPLICATION_JSON).post(
                Entity.entity(multipartEntity, MediaType.MULTIPART_FORM_DATA));
        if(null != response){
            if(200 == response.getStatus()){
                success ++;
            }
        }
        executed ++ ;
        if(executed == tps){
            System.out.println("耗时 ：：" + (System.currentTimeMillis() - start) + "ms");
            System.out.println("总数 : " + tps);
            System.out.println("成功数 : " + success);
        }
    }

    private static Client getClient(){
        if(null == client){
            ClientConfig config = new ClientConfig();
            client = ClientBuilder.newClient(config);
            client.register(MultiPartFeature.class);
        }
        return client;
    }

    private static class CustomThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }

    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                //阻塞方法
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                System.out.println("放入阻塞队列失败！！！");
                e.printStackTrace();
            }
        }
    }

    private static class MyRunnable implements Runnable{
        @Override
        public void run() {
            JSONObject content = new JSONObject();
            content.put("license",getCarLicense(executed));
            content.put("parkId",parkId);
            doPostRequest(content.toJSONString());
        }
    }
}
