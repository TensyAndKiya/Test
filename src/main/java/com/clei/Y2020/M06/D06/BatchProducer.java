package com.clei.Y2020.M06.D06;

import com.clei.utils.PrintUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KIyA
 * @date 2020-04-08
 *
 * 批量发送
 */
public class BatchProducer {
    public static void main(String[] args) throws Exception{
        // 初始化一个producer group
        DefaultMQProducer producer = new DefaultMQProducer("FirstGroup");

        // 设置mq name server address
        producer.setNamesrvAddr("127.0.0.1:9876");

        // 启动
        producer.start();

        List<Message> list = new ArrayList<>(100);

        for (int i = 0; i < 100; i++) {
            // 创建消息
            Message msg = new Message("FirstTopic","FirstTag",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            list.add(msg);
        }

        // 发送
        SendResult result = producer.send(list);

        PrintUtil.dateLine(" 发送结果：" + result);

        // 关闭
        producer.shutdown();

    }
}
