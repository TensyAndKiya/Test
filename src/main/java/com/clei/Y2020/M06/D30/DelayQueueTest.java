package com.clei.Y2020.M06.D30;


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

        System.out.println(q1.getStopMillis());
        System.out.println(q2.getStopMillis());
        System.out.println(q3.getStopMillis());

        DelayQueue<QueryResult> queue = new DelayQueue<>();

        queue.add(q1);
        queue.add(q2);
        queue.add(q3);

        System.out.println(queue.size());

        for (int i = 0; i < queue.size(); i++) {

            QueryResult result = queue.take();
            System.out.println(System.currentTimeMillis() + " delay : " + result.getDelay(TimeUnit.MILLISECONDS) + " stop : " + result.getStopMillis());

        }

        Thread.sleep(10000);

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
