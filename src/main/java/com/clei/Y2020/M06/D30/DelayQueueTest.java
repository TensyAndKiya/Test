package com.clei.Y2020.M06.D30;

import com.clei.utils.PrintUtil;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue 学习一波
 */
public class DelayQueueTest {

    public static void main(String[] args) throws Exception{

        long curMillis = System.currentTimeMillis();

        QueryResult q1 = new QueryResult(curMillis + 3000);

        QueryResult q2 = new QueryResult(curMillis + 6000);

        QueryResult q3 = new QueryResult(curMillis + 9000);

        PrintUtil.log(q1.getStopMillis());
        PrintUtil.log(q2.getStopMillis());
        PrintUtil.log(q3.getStopMillis());

        DelayQueue<QueryResult> queue = new DelayQueue<>();

        queue.add(q1);
        queue.add(q2);
        queue.add(q3);

        int size = queue.size();
        PrintUtil.log(queue.size());

        // take之后会改变queue的size 所以不要用queue.size()控制循环
        for (int i = 0; i < size; i++) {
            QueryResult result = queue.take();
            PrintUtil.log(System.currentTimeMillis() + " delay : " + result.getDelay(TimeUnit.MILLISECONDS) + " stop : " + result.getStopMillis());
        }
    }
}

class QueryResult implements Delayed{

    private long stopMillis;

    public QueryResult(long stopMillis){
        this.stopMillis = stopMillis;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(stopMillis - System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {

        QueryResult obj = (QueryResult) o;

        long sm = obj.getDelay(TimeUnit.MILLISECONDS);

        if(sm == stopMillis){
            return 0;
        }

        if(sm < stopMillis){
            return 1;
        }

        return -1;
    }

    public long getStopMillis(){
        return stopMillis;
    }
}
