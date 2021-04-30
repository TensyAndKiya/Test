package com.clei.Y2020.M06.D06;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 消费者 广播
 *
 * @author KIyA
 * @date 2020-06-06
 */
public class BroadcastConsumer {
    public static void main(String[] args) throws Exception {
        consume();
    }

    private static void consume() throws Exception {
        // 初始化消费者并设置组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroup1");

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);

        // 设置 ms nameServer address
        consumer.setNamesrvAddr("127.0.0.1:9876");

        // 订阅topic
        consumer.subscribe("FirstTopic", "*");

        // 注册消息处理监听器 MessageListenerConcurrently 多线程处理
        consumer.registerMessageListener((List<MessageExt> messages, ConsumeConcurrentlyContext context) -> {

            Consumer.handleMsg(messages);

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        // 启动
        consumer.start();
    }
}
