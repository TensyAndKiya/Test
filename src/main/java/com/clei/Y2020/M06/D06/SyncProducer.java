package com.clei.Y2020.M06.D06;

import com.clei.utils.PrintUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author KIyA
 * @date 2020-04-08
 * <p>
 * 可靠同步发送 运用在比较重要一点消息传递/通知等业务
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        send();
    }

    private static void send() throws Exception {
        // 初始化一个producer group
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroup1");

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
            SendResult result = producer.send(msg, selector, 1);

            PrintUtil.log(i + " 发送结果：" + result);
        }

        // 关闭
        producer.shutdown();
    }
}
