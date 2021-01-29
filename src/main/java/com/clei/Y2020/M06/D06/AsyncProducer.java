package com.clei.Y2020.M06.D06;

import com.clei.utils.PrintUtil;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author KIyA
 * @date 2020-04-08
 *
 * 异步发送
 */
public class AsyncProducer {
    public static void main(String[] args) throws Exception{
        // 初始化一个producer group
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroup1");

        // 设置mq server address
        producer.setNamesrvAddr("127.0.0.1:9876;127.0.0.1:9877");

        // producer.setInstanceName("allMNoS");

        // 启动
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
            PrintUtil.log("mq 启动失败！");
            return;
        }

        // 异步发送失败重试次数
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 100;

        CountDownLatch countDownLatch = new CountDownLatch(messageCount);

        try{
            for (int i = 0; i < messageCount; i++) {
                final int index = i;

                // 创建消息
                Message msg = new Message("FirstTopic", "FirstTag", "FirstKey" + i,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

                //
                // msg.setDelayTimeLevel(0);

                // 发送
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch.countDown();
                        PrintUtil.log(index + " 发送成功 结果：" + sendResult);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        countDownLatch.countDown();
                        throwable.printStackTrace();
                        PrintUtil.log(index + " 发送失败 ");
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
            PrintUtil.log("mq 发送消息失败！");
        }

        boolean result = countDownLatch.await(5, TimeUnit.SECONDS);

        PrintUtil.log("result : " + result);

        // 关闭
        producer.shutdown();
    }
}
