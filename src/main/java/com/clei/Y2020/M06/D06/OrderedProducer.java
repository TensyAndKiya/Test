package com.clei.Y2020.M06.D06;

import com.clei.utils.PrintUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author KIyA
 * @date 2020-04-08
 *
 * 有序发送
 */
public class OrderedProducer {
    public static void main(String[] args) throws Exception{
        // 初始化一个producer group
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroup1");

        // 设置mq name server address
        producer.setNamesrvAddr("127.0.0.1:9876");

        // 启动
        producer.start();

        String[] tags = {"tagA","tagB","tagC","tagD","tagE"};

        MessageQueueSelector selector = (messageQueues, msg, obj) -> {
            Integer id = (Integer) obj;
            int index = id % messageQueues.size();
            return messageQueues.get(index);
        };

        for (int i = 0; i < 100; i++) {

            int orderId = i % 10;

            // 创建消息
            Message msg = new Message("FirstTopic",tags[i % tags.length], "KEY_" + i,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            // 发送
            // 指定消息存储在哪个队列中
            SendResult result = producer.send(msg,selector,orderId);

            PrintUtil.log(i + " 发送结果：" + result);
        }

        // 关闭
        producer.shutdown();

    }
}
