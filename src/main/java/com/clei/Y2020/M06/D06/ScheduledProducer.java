package com.clei.Y2020.M06.D06;

import com.clei.utils.PrintUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author KIyA
 * @date 2020-04-08
 * <p>
 * rocketmq实现延时任意时间的消息
 * <p>
 * 1. 在rocketmq broker配置想要的的级别 支持s、m、h、d（秒、分、时、天）单位
 * 缺点：每多一种没有的级别就要修改配置
 * <p>
 * 2. 针对延时消息设计一种消息格式，根据延时时间先发一个当前支持延时级别的
 * 消息，消息内的剩余延时时间减去对应级别的时间，直到剩余时间刚好等于某个
 * 延时级别的时间
 * 例：延时333s 先发延时5m的消息{33s,msg} 再发延时30s的消息{3s,msg}
 * 再发延时1s的{2s,msg}...{1s,msg}...{msg}
 * 缺点：需要发送的消息数量增加了
 * <p>
 * 延时发送
 */
public class ScheduledProducer {

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

        for (int i = 0; i < 100; i++) {
            // 创建消息
            Message msg = new Message("FirstTopic", "FirstTag",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            // 延时级别
            msg.setDelayTimeLevel(3);

            // 发送
            SendResult result = producer.send(msg);

            PrintUtil.log(i + " 发送结果：" + result);
        }

        // 关闭
        producer.shutdown();
    }
}
