package com.clei.Y2019.M08.D10;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.PrintUtil;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.annotation.Nonnull;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author KIyA
 */
public class JerseyClientTest {

    private static final String PARK_ID = "ABANDON";
    private static String prefix;
    private static int tps;
    private static final AtomicInteger SUCCESS = new AtomicInteger(0);

    public static void main(String[] args) {
        int paramNum = 2;

        if (args.length == paramNum) {
            prefix = args[0];
            tps = Integer.parseInt(args[1]);
        } else {
            PrintUtil.dateLine("参数不对！！！");
        }

        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(tps);
        doPost(tps, latch);

        PrintUtil.dateLine("耗时 ：：" + (System.currentTimeMillis() - start) + "ms");
        PrintUtil.dateLine("总数 : " + tps);
        PrintUtil.dateLine("成功数 : " + SUCCESS);
    }

    private static void doPost(int tps, CountDownLatch latch) {

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
            MyRunnable runnable = new MyRunnable(latch, i);
            executor.submit(runnable);
        }
    }

    private static String getCarLicense(int i) {
        int properLength = 6;
        StringBuilder str = new StringBuilder();
        str.append(i);
        for (int j = 0; j < properLength - str.length(); j++) {
            str.insert(0, '0');
        }
        return prefix + str;
    }

    private static void doPostRequest(String content) {
        WebTarget target = getClient().target("http://127.0.0.1:8888/park/rs/cloudRequest/cloudEnReq");
        MultiPart multipartEntity = new FormDataMultiPart().field("condition", content, MediaType.APPLICATION_JSON_TYPE);
        Response response = target.request().accept(MediaType.APPLICATION_JSON).post(
                Entity.entity(multipartEntity, MediaType.MULTIPART_FORM_DATA));
        int successStatus = 200;
        if (null != response) {
            if (successStatus == response.getStatus()) {
                SUCCESS.addAndGet(1);
            }
        }
    }

    private static Client getClient() {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        client.register(MultiPartFeature.class);
        return client;
    }

    private static class CustomThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(@Nonnull Runnable r) {
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
                PrintUtil.dateLine("放入阻塞队列失败！！！");
                e.printStackTrace();
            }
        }
    }

    private static class MyRunnable implements Runnable {

        private final CountDownLatch latch;
        private final int i;

        public MyRunnable(CountDownLatch latch, int i) {
            this.i = i;
            this.latch = latch;
        }

        @Override
        public void run() {
            JSONObject content = new JSONObject();
            content.put("license", getCarLicense(i));
            content.put("parkId", PARK_ID);
            doPostRequest(content.toJSONString());
            latch.countDown();
        }
    }
}