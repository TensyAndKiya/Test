package com.clei.Y2020.M06.D06;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户自定义事务监听器
 *
 * @author KIyA
 * @date 2020-06-19
 */
public class TransactionListenerImpl implements TransactionListener {

    private AtomicInteger transactionIndex = new AtomicInteger(0);

    private ConcurrentHashMap<String,Integer> localTrans = new ConcurrentHashMap<>();

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {

        PrintUtil.dateLine(DateUtil.currentDateTime() + " message : " + message + " obj : " + o);

        Integer value = transactionIndex.getAndIncrement();

        int status = value % 3;

        localTrans.put(message.getTransactionId(),status);

        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {

        PrintUtil.dateLine(DateUtil.currentDateTime() + "  " + messageExt);

        Integer status = localTrans.get(messageExt.getTransactionId());

        if(null != status){

            switch (status){
                case 0:
                    return LocalTransactionState.UNKNOW;
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                default:
                    return LocalTransactionState.COMMIT_MESSAGE;
            }

        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
