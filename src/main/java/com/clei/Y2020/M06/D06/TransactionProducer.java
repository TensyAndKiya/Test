package com.clei.Y2020.M06.D06;

import com.clei.utils.PrintUtil;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author KIyA
 * @date 2020-06-09
 *
 * 事务消息发送
 */
public class TransactionProducer {
    public static void main(String[] args) throws Exception{
        // 初始化一个producer group
        TransactionMQProducer producer = new TransactionMQProducer("TransactionProducerGroup1");

        // 设置mq name server address
        producer.setNamesrvAddr("127.0.0.1:9876");

        // 线程池
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200),
                new ThreadFactory() {
                    private AtomicInteger threadNum = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("transaction-msg-check-" + threadNum.getAndIncrement());
                        return thread;
                    }
                });

        producer.setExecutorService(executorService);
        producer.setTransactionListener(new TransactionListenerImpl());

        // 启动
        producer.start();

        for (int i = 0; i < 10; i++) {
            // 创建消息
            Message msg = new Message("FirstTopic","FirstTag",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            // 发送
            // SendResult result = producer.send(msg);
            // 指定消息存储在哪个队列中
            SendResult result = producer.sendMessageInTransaction(msg,Integer.valueOf(i));

            PrintUtil.log(i + " 发送结果：" + result);
        }

        Thread.sleep(10000000);

        // 关闭
        producer.shutdown();

    }
}
