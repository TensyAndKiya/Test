package com.clei.Y2020.M08.D20;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * 10人赛跑
 *
 * @author KIyA
 */
public class TenPersonRace {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(10);

        CopyOnWriteArrayList<Score> list = new CopyOnWriteArrayList<>();

        int person = 10;
        for (int i = 0; i < person; i++) {

            String name = "person " + i;

            new Thread(() -> {

                try {
                    latch1.await();

                    System.out.println(name + "出发");

                    int result = new Random().nextInt(1000);

                    list.add(new Score(name, result));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch2.countDown();
                }

            }, name).start();
        }

        // 比赛开始
        latch1.countDown();

        // 等待结束
        latch2.await();

        // 排序
        List<Score> sList = list.stream().sorted(Comparator.comparingInt(Score::getScore).reversed()).collect(Collectors.toList());

        // 全部
        System.out.println("全部");
        sList.forEach(System.out::println);

        // 前三名
        System.out.println("前三");
        sList.stream().limit(3).forEach(System.out::println);

        // 平均成绩
        System.out.println("平均成绩");
        double avg = sList.stream().mapToInt(Score::getScore).average().orElse(0D);
        System.out.println(avg);

    }

    static class Score {

        private String name;
        private int score;

        public Score(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return "Score{" +
                    "name='" + name + '\'' +
                    ", score=" + score +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

}
