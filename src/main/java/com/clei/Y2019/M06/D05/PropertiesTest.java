package com.clei.Y2019.M06.D05;

import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;
import com.clei.utils.ThreadUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 属性文件读取与修改
 *
 * @author KIyA
 */
public class PropertiesTest {

    /**
     * 配置文件写锁
     */
    private final static ReentrantReadWriteLock.WriteLock WRITE_LOCK = new ReentrantReadWriteLock().writeLock();

    /**
     * 配置文件修改次数
     */
    private static int updateTimes = 0;

    /**
     * 负载文件名
     */
    private final static String LOAD_FILE_NAME = "/other/load.properties";

    /**
     * 限制阀值key
     */
    private final static String THRESHOLD_KEY = "limit_threshold";

    /**
     * 是否开启限制key 1是 其它否
     */
    private final static String LIMIT_OPEN_KEY = "limit_open";

    /**
     * 开启限制时 LIMIT_OPEN_KEY对应的值
     */
    private final static String LIMIT_OPEN_VALUE = "1";

    /**
     * 当开启限制时 不受限制的车场的id
     * 多个id以英文逗号隔开
     */
    private final static String FREE_PARKS_KEY = "free_parks";

    /**
     * 多个车场的分隔符
     */
    private final static String PARKS_SEPARATOR = ",";

    /**
     * 配置文件路径
     */
    private final static String PATH = PropertiesTest.class.getResource(LOAD_FILE_NAME).getPath();

    public static void main(String[] args) throws Exception {
        Properties prop = getPropByFile();
        if (null == prop) {
            return;
        }
        List<String> freeParks = getFreeParks(prop);
        PrintUtil.log("Free Park 数量： {}", freeParks.size());
        if (!freeParks.isEmpty()) {
            freeParks.forEach(PrintUtil::log);
        }
        // 尝试修改
        updateThreshold(prop, 33);
        // 并发修改
        ThreadPoolExecutor pool = ThreadUtil.pool();
        int times = 100;
        CountDownLatch latch = new CountDownLatch(times);
        for (int i = 0; i < times; i++) {
            int threshold = i;
            pool.execute(() -> {
                updateThreshold(prop, threshold);
                latch.countDown();
            });
        }
        latch.await();
        pool.shutdown();
        PrintUtil.log("updateTimes : {}", updateTimes);
    }

    /**
     * 读取配置文件
     *
     * @return
     */
    private static Properties getPropByFile() {
        File file = new File(PATH);
        if (!file.exists()) {
            PrintUtil.log("读取负载配置文件失败，文件不存在！");
            return null;
        }
        try (InputStream inputStream = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            PrintUtil.log("读取负载配置文件出错", e);
            return null;
        }
    }

    /**
     * 获取不受限制的车场
     *
     * @param prop
     * @return
     */
    private static List<String> getFreeParks(Properties prop) {
        if (null == prop) {
            prop = getPropByFile();
            if (null == prop) {
                return Collections.emptyList();
            }
        }
        String parks = prop.getProperty(FREE_PARKS_KEY);
        if (StringUtil.isNotBlank(parks)) {
            return new ArrayList<>(Arrays.asList(parks.split(PARKS_SEPARATOR)));
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 修改限制阀值
     *
     * @param prop      属性文件
     * @param threshold 要设置的阀值
     * @return
     */
    private static void updateThreshold(Properties prop, int threshold) {
        if (threshold < 0 || threshold > 100) {
            throw new RuntimeException("参数错误");
        }
        if (null == prop) {
            prop = getPropByFile();
            if (null == prop) {
                return;
            }
        }
        String newVal = String.valueOf(threshold);
        String oldVal = prop.getProperty(THRESHOLD_KEY);
        boolean updateResult;
        if (newVal.equals(oldVal)) {
            PrintUtil.log("新阀值与旧阀值一致，不必更新");
            updateResult = true;
        } else {
            prop.setProperty(THRESHOLD_KEY, newVal);
            updateResult = updateProp(prop, null);
        }
        PrintUtil.log("将负载阀值修改为{}，{}", threshold, updateResult ? "成功" : "失败");
    }

    private static boolean updateProp(Properties prop, String comment) {
        if (null == prop) {
            return false;
        }
        File file = new File(PATH);
        if (!file.exists()) {
            PrintUtil.log("写入负载配置文件失败，文件不存在！");
            return false;
        }
        boolean updateResult = false;
        // 加锁 放到try外面避免加锁失败后执行unlock报错
        // 放到try外面能保证执行lock后面的语句时已经加锁成功了
        WRITE_LOCK.lock();
        try (OutputStream outputStream = new FileOutputStream(file)) {
            prop.store(outputStream, comment);
            updateResult = true;
            // 更新配置文件修改次数
            updateTimes++;
        } catch (IOException e) {
            PrintUtil.log("更新配置文件出错", e);
        } finally {
            WRITE_LOCK.unlock();
        }
        return updateResult;
    }
}
