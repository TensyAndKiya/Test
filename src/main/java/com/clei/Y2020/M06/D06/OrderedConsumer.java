package com.clei.Y2020.M06.D06;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 消费者
 *
 * @author KIyA
 * @date 2020-06-06
 * <p>
 * 有序消费
 */
public class OrderedConsumer {

    public static void main(String[] args) throws Exception {
        consume();
    }

    private static void consume() throws Exception {
        // 初始化消费者并设置组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroup1");

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 设置 ms nameServer address
        consumer.setNamesrvAddr("127.0.0.1:9876");

        // 订阅topic
        consumer.subscribe("FirstTopic", "tagA || tagC || tagD");

        // 注册消息处理监听器 MessageListenerOrderly 单线程处理
        consumer.registerMessageListener(new MessageListenerOrderly() {

            AtomicLong consumTimes = new AtomicLong(0);

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> messages, ConsumeOrderlyContext context) {

                context.setAutoCommit(false);

                Consumer.handleMsg(messages);

                this.consumTimes.incrementAndGet();

                if (this.consumTimes.get() % 2 == 0) {

                    return ConsumeOrderlyStatus.SUCCESS;

                } else if (this.consumTimes.get() % 5 == 0) {

                    context.setSuspendCurrentQueueTimeMillis(3000);

                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;

                } else {

                    return ConsumeOrderlyStatus.SUCCESS;

                }
            }
        });

        // 启动
        consumer.start();
    }
}
