package com.clei.Y2019.M08.D16;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorTest {
    public static void main(String[] args) {
        PrintUtil.dateLine("开始时间 " + DateUtil.format(LocalDateTime.now()));
        postAgain("", "内容", 3);
    }
    private static void postAgain(final String url,final String json,final int times){
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        // 5秒后执行一次
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                int  result = getRand(10);
                if(result == 0){
                    PrintUtil.println("{} 第{}次推送成功，参数:{}", DateUtil.format(LocalDateTime.now()),times,json);
                }else{
                    PrintUtil.println("{} 第{}次推送失败，参数:{}",DateUtil.format(LocalDateTime.now()),times,json);
                    postAgain(url,json,times + 1);
                }
                // 执行完后记得关闭线程池。。
                executor.shutdown();
            }
        },5, TimeUnit.SECONDS);
    }

    private static int getRand(int limit){
        Random rand = new Random();
        return rand.nextInt(limit);
    }
}
