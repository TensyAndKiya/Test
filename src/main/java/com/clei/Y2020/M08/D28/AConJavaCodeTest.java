package com.clei.Y2020.M08.D28;

import com.clei.utils.PrintUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 看见一段处理并发问题的代码，总感觉有点问题，测试一下
 * 添加车辆
 *
 * @author KIyA
 */
public class AConJavaCodeTest {

    public static void main(String[] args) throws InterruptedException {

        operation(0);
    }

    /**
     * 操作起来
     *
     * @param type 0 原操作
     *             其他 我的操作
     */
    private static void operation(int type) throws InterruptedException {

        // 线程数
        int tasks = 1000;

        // park数量
        int parks = 100000;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                8,
                17,
                1,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(tasks)
        );

        // 操作
        AddVehicleOperation operation;

        // 数据结构
        final ConcurrentHashMap<Long, List<VehicleInfo>> map = new ConcurrentHashMap<>(10);

        // 原操作
        if (0 == type) {

            operation = AConJavaCodeTest::originalAddVehicle;

        } else {
            // 我的操作

            operation = AConJavaCodeTest::myAddVehicle;

        }

        final AddVehicleOperation op = operation;

        // 开始
        long start = System.currentTimeMillis();

        CountDownLatch latch = new CountDownLatch(tasks);

        for (int i = 0; i < tasks; i++) {

            executor.submit(() -> {

                for (int j = 0; j < parks; j++) {

                    long parkId = 12345 + j;

                    byte b = 0;

                    op.operate(map, parkId, "black", b);

                }

                latch.countDown();

            });
        }

        latch.await();
        // 结束
        long end = System.currentTimeMillis();

        executor.shutdown();

        int size = 0;


        Iterator<Map.Entry<Long, List<VehicleInfo>>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, List<VehicleInfo>> entry = it.next();
            size += entry.getValue().size();
        }

        PrintUtil.log("size : " + size);

        PrintUtil.println("操作：{} 耗时：{}ms", type, end - start);

    }

    /**
     * 原添加方法
     *
     * @param vehicleMap
     * @param parkId
     * @param vehicleNo
     * @param vehicleColor
     */
    private static synchronized void originalAddVehicle(ConcurrentHashMap<Long, List<VehicleInfo>> vehicleMap, long parkId, String vehicleNo, byte vehicleColor) {
        List<VehicleInfo> vehicleInfoList = vehicleMap.get(parkId);
        if (vehicleInfoList == null) {
            vehicleInfoList = new CopyOnWriteArrayList<>();
        } else if (vehicleInfoList.size() > 0) {
            List<VehicleInfo> existList = vehicleInfoList.stream()
                    .filter(v -> v.getVehicleNo().equals(vehicleNo)
                            && v.getVehicleColor() == vehicleColor)
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
            if (existList.size() > 0) {
                return;
            }
        }
        VehicleInfo vehicleInfo = new VehicleInfo();
        vehicleInfo.setParkId(parkId);
        vehicleInfo.setVehicleColor(vehicleColor);
        vehicleInfo.setVehicleNo(vehicleNo);
        vehicleInfo.setAcquiredBaseInfo(false);
        vehicleInfoList.add(vehicleInfo);
        vehicleMap.put(parkId, vehicleInfoList);
    }

    /**
     * 我的添加方法
     *
     * @param vehicleMap
     * @param parkId
     * @param vehicleNo
     * @param vehicleColor
     */
    private static synchronized void myAddVehicle(Map<Long, List<VehicleInfo>> vehicleMap, long parkId, String vehicleNo, byte vehicleColor) {
        List<VehicleInfo> vehicleInfoList = vehicleMap.get(parkId);
        if (vehicleInfoList == null) {

            // synchronized (String.valueOf(parkId).intern()) parkId太多不同值会导致常量池堆积大量数据
            Long lock = getCache(parkId);
            synchronized (lock) {

                vehicleInfoList = vehicleMap.get(parkId);

                if (vehicleInfoList == null) {

                    vehicleInfoList = new CopyOnWriteArrayList<>();

                    vehicleMap.put(parkId, vehicleInfoList);

                }

            }

        }
        if (vehicleInfoList.size() > 0) {
            Optional<VehicleInfo> existVehicle = vehicleInfoList.stream()
                    .filter(v -> v.getVehicleNo().equals(vehicleNo) && v.getVehicleColor() == vehicleColor)
                    .findFirst();
            if (existVehicle.isPresent()) {
                return;
            }
        }
        VehicleInfo vehicleInfo = new VehicleInfo();
        vehicleInfo.setParkId(parkId);
        vehicleInfo.setVehicleColor(vehicleColor);
        vehicleInfo.setVehicleNo(vehicleNo);
        vehicleInfo.setAcquiredBaseInfo(false);
        vehicleInfoList.add(vehicleInfo);
    }

    /**
     * 获取缓存池里的Long对象
     *
     * @param l
     * @return
     */
    private static Long getCache(long l) {
        // 使得 -127 <= l <= 127
        l = l % 128;
        // 通过自动装箱获取到缓冲池里的对象
        return l;
    }


    /**
     * 车辆信息类
     */
    static class VehicleInfo {
        private long parkId;
        private String vehicleNo;
        private byte vehicleColor;
        private boolean acquiredBaseInfo;

        public long getParkId() {
            return parkId;
        }

        public void setParkId(long parkId) {
            this.parkId = parkId;
        }

        public String getVehicleNo() {
            return vehicleNo;
        }

        public void setVehicleNo(String vehicleNo) {
            this.vehicleNo = vehicleNo;
        }

        public byte getVehicleColor() {
            return vehicleColor;
        }

        public void setVehicleColor(byte vehicleColor) {
            this.vehicleColor = vehicleColor;
        }

        public boolean isAcquiredBaseInfo() {
            return acquiredBaseInfo;
        }

        public void setAcquiredBaseInfo(boolean acquiredBaseInfo) {
            this.acquiredBaseInfo = acquiredBaseInfo;
        }
    }

    /**
     * 添加车辆操作
     */
    interface AddVehicleOperation {
        void operate(ConcurrentHashMap<Long, List<VehicleInfo>> vehicleMap, long parkId, String vehicleNo, byte vehicleColor);
    }

}
