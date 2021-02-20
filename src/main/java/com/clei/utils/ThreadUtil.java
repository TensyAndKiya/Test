package com.clei.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工具类
 *
 * @author KIyA
 */
public class ThreadUtil {

    /**
     * 核心线程数
     */
    private final static int CORE_POOL_SIZE = 4;

    /**
     * 最大线程数
     */
    private final static int MAX_POOL_SIZE = 9;

    /**
     * 保持活跃时间单位
     */
    private final static TimeUnit TIME_UNIT = TimeUnit.MINUTES;

    /**
     * 保持活跃时间
     */
    private final static int KEEP_ALIVE_TIME = 1;

    /**
     * 队列容量
     */
    private final static int QUEUE_CAPACITY = 20;

    /**
     * 默认工厂名
     */
    private final static String FACTORY_NAME = "custom";

    /**
     * 工厂名map
     */
    private final static ConcurrentHashMap<String, AtomicInteger> FACTORY_MAP = new ConcurrentHashMap<>(4);

    /**
     * 获取一个使用默认参数的池
     *
     * @return
     */
    public static ThreadPoolExecutor pool() {
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                new LinkedBlockingQueue<>(QUEUE_CAPACITY),
                factory(FACTORY_NAME),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * 获取线程工厂
     *
     * @param factoryName
     * @return
     */
    public static ThreadFactory factory(String factoryName) {
        if (FACTORY_MAP.containsKey(factoryName)) {
            throw new RuntimeException("工厂名已存在");
        }
        AtomicInteger i = new AtomicInteger(1);
        FACTORY_MAP.put(factoryName, i);
        return (r) -> {
            String threadName = factoryName + '_' + i.getAndAdd(1);
            return new Thread(r, threadName);
        };
    }

}
