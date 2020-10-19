package com.clei.Y2020.M06.D06;

import com.clei.utils.PrintUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author KIyA
 * @date 2020-04-08
 *
 * 添加用户自定义属性 用于过滤
 */
public class FilterProducer {
    public static void main(String[] args) throws Exception{
        // 初始化一个producer group
        DefaultMQProducer producer = new DefaultMQProducer("FirstGroup");

        // 设置mq name server address
        producer.setNamesrvAddr("127.0.0.1:9876");

        // 启动
        producer.start();

        for (int i = 0; i < 100; i++) {
            // 创建消息
            Message msg = new Message("FirstTopic","FirstTag",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            // 自定义属性
            msg.putUserProperty("index",String.valueOf(i));
            msg.putUserProperty("userKey","filter");

            // 发送
            SendResult result = producer.send(msg);

            PrintUtil.dateLine(i + " 发送结果：" + result);
        }

        // 关闭
        producer.shutdown();

    }
}
