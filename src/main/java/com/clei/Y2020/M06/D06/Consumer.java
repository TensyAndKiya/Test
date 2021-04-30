package com.clei.Y2020.M06.D06;

import com.clei.utils.PrintUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消费者
 *
 * @author KIyA
 * @date 2020-06-06
 */
public class Consumer {

    private final static AtomicInteger MESSAGE_COUNT = new AtomicInteger(0);

    public static void main(String[] args) throws MQClientException {

        PrintUtil.log(System.currentTimeMillis());

        // 初始化消费者并设置组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroup1");

        // 设置 ms nameServer address
        consumer.setNamesrvAddr("127.0.0.1:9876;127.0.0.1:9877");

        // 订阅topic
        consumer.subscribe("FirstTopic", "*");

        // 默认消费模式是CLUSTERING
        PrintUtil.log("消费模式： {}", consumer.getMessageModel());
        /*consumer.setMessageModel(MessageModel.BROADCASTING);
        PrintUtil.log("消费模式： {}", consumer.getMessageModel());*/

        // 注册消息处理监听器 MessageListenerConcurrently 多线程处理
        /*consumer.registerMessageListener((List<MessageExt> messages, ConsumeConcurrentlyContext context) -> {

            handleMsg(messages);

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });*/
        // 注册消息处理监听器 MessageListenerOrderly 单线程处理
        consumer.registerMessageListener((List<MessageExt> messages, ConsumeOrderlyContext context) -> {

            handleMsg(messages);

            return ConsumeOrderlyStatus.SUCCESS;
        });

        // 启动
        consumer.start();
    }

    private static void handleMsg(List<MessageExt> messages) {
        Thread thread = Thread.currentThread();
        String threadId = thread.getName() + '_' + thread.getId();

        PrintUtil.log("{} 收到新消息 : {}", threadId, messages);

        for (MessageExt msg : messages) {
            PrintUtil.log("{} topic : {}", threadId, msg.getTopic());
            PrintUtil.log("{} tag : {}", threadId, msg.getTags());
            PrintUtil.log("{} msgId : {}", threadId, msg.getMsgId());
            PrintUtil.log("{} body : {}", threadId, new String(msg.getBody(), StandardCharsets.UTF_8));
        }

        PrintUtil.log("收到消息数量 : {}", MESSAGE_COUNT.addAndGet(1));
    }
}
