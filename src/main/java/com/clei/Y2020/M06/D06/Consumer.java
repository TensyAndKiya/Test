package com.clei.Y2020.M06.D06;

import com.clei.utils.PrintUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 消费者
 *
 * @author KIyA
 * @date 2020-06-06
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {

        // 初始化消费者并设置组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("FirstGroup");

        // 设置 ms nameServer address
        consumer.setNamesrvAddr("127.0.0.1:9876");

        // 订阅topic
        consumer.subscribe("FirstTopic","*");

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

    private static void handleMsg(List<MessageExt> messages){
        Thread thread = Thread.currentThread();
        String threadId = thread.getName() + '_' + thread.getId();

        PrintUtil.println("{} 收到新消息 : {}",threadId,messages);

        for (MessageExt msg : messages){
            PrintUtil.println("{} topic : {}", threadId, msg.getTopic());
            PrintUtil.println("{} tag : {}", threadId, msg.getTags());
            PrintUtil.println("{} msgId : {}", threadId, msg.getMsgId());
            try {
                PrintUtil.println("{} body : {}", threadId, new String(msg.getBody(),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
