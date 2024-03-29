package com.clei.Y2020.M06.D06;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消费者 过滤
 *
 * @author KIyA
 * @date 2020-06-06
 */
public class FilterConsumer {
    public static void main(String[] args) throws Exception {
        consume();
    }

    private static void consume() throws Exception {
        // 初始化消费者并设置组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroup1");

        // 设置 ms nameServer address
        consumer.setNamesrvAddr("127.0.0.1:9876");

        // 订阅topic
        // 根据用户自定义属性过滤
        // 要使用属性过滤 要在broker.conf添加配置 enablePropertyFilter=true
        consumer.subscribe("FirstTopic", MessageSelector.bySql("index between 45 and 55 and userKey = 'filter'"));

        // 注册消息处理监听器 MessageListenerConcurrently 多线程处理
        consumer.registerMessageListener((List<MessageExt> messages, ConsumeConcurrentlyContext context) -> {

            Consumer.handleMsg(messages);

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        // 启动
        consumer.start();
    }
}
