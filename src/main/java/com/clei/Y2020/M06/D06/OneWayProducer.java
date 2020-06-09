package com.clei.Y2020.M06.D06;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author KIyA
 * @date 2020-04-08
 *
 * 单向发送
 * 适用于某些耗时非常短，
 * 但对可靠性要求并不高的场景，例如日志收集
 * 只发送消息，不等待服务器响应，
 * 只发送请求不等待应答。
 * 此方式发送消息的过程耗时非常短，
 * 一般在微秒级别。
 */
public class OneWayProducer {
    public static void main(String[] args) throws Exception {
        // 初始化一个producer group
        DefaultMQProducer producer = new DefaultMQProducer("FirstGroup");

        // 设置mq name server address
        producer.setNamesrvAddr("127.0.0.1:9876");

        // 启动
        producer.start();

        MessageQueueSelector selector = new SelectMessageQueueByHash();

        for (int i = 0; i < 100; i++) {
            // 创建消息
            Message msg = new Message("FirstTopic","FirstTag",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            // 发送
            // SendResult result = producer.send(msg);
            // 指定消息存储在哪个队列中
            producer.sendOneway(msg, selector, Integer.valueOf(1));
        }

        // 等待发送完成
        // Thread.sleep(5000);

        // 关闭
        producer.shutdown();

    }
}
