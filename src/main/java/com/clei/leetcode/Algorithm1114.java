package com.clei.leetcode;

import com.clei.utils.PrintUtil;

import java.util.concurrent.locks.LockSupport;

/**
 * 按序打印
 * 我们提供了一个类：
 * <p>
 * public class Foo {
 * public void first() { print("first"); }
 * public void second() { print("second"); }
 * public void third() { print("third"); }
 * }
 * 三个不同的线程 A、B、C 将会共用一个 Foo 实例。
 * <p>
 * 一个将会调用 first() 方法
 * 一个将会调用 second() 方法
 * 还有一个将会调用 third() 方法
 * 请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行。
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * 输入: [1,2,3]
 * 输出: "firstsecondthird"
 * 解释:
 * 有三个线程会被异步启动。
 * 输入 [1,2,3] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 second() 方法，线程 C 将会调用 third() 方法。
 * 正确的输出是 "firstsecondthird"。
 * 示例 2:
 * <p>
 * 输入: [1,3,2]
 * 输出: "firstsecondthird"
 * 解释:
 * 输入 [1,3,2] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 third() 方法，线程 C 将会调用 second() 方法。
 * 正确的输出是 "firstsecondthird"。
 *
 * @author KIyA
 */
public class Algorithm1114 {

    public static void main(String[] args) {
        call(new int[]{3, 2, 1});
    }

    private static void call(int[] arr) {
        // Foo1 foo = new Foo1();
        // Foo2 foo = new Foo2();
        // Foo3 foo = new Foo3();
        Foo4 foo = new Foo4();
        for (int i : arr) {
            if (1 == i) {
                new Thread(() -> {
                    try {
                        foo.first(() -> PrintUtil.log("first"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else if (i == 2) {
                new Thread(() -> {
                    try {
                        foo.second(() -> PrintUtil.log("second"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else if (i == 3) {
                new Thread(() -> {
                    try {
                        foo.third(() -> PrintUtil.log("third"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }


    private interface Foo {

        void first(Runnable printFirst) throws InterruptedException;

        void second(Runnable printSecond) throws InterruptedException;

        void third(Runnable printThird) throws InterruptedException;
    }

    /**
     * 可能存在多个方法在while等很久
     * 不推荐
     */
    private static class Foo1 implements Foo {

        // 可以替换成AtomicInteger
        private volatile int order = 1;

        @Override
        public void first(Runnable printFirst) throws InterruptedException {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            order = 2;
        }

        @Override
        public void second(Runnable printSecond) throws InterruptedException {
            while (order != 2) {

            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            order = 3;
        }

        @Override
        public void third(Runnable printThird) throws InterruptedException {
            while (order != 3) {

            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }

    private static class Foo2 implements Foo {

        private volatile Thread t1;
        private volatile Thread t2;

        @Override
        public void first(Runnable printFirst) throws InterruptedException {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            t1 = Thread.currentThread();
        }

        @Override
        public void second(Runnable printSecond) throws InterruptedException {
            while (null == t1) {

            }
            t1.join();
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            t2 = Thread.currentThread();
        }

        @Override
        public void third(Runnable printThird) throws InterruptedException {
            while (null == t2) {

            }
            t2.join();
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }

    private static class Foo3 implements Foo {

        private volatile Thread t2;
        private volatile Thread t3;

        @Override
        public void first(Runnable printFirst) throws InterruptedException {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            while (null == t2) {

            }
            LockSupport.unpark(t2);
        }

        @Override
        public void second(Runnable printSecond) throws InterruptedException {
            t2 = Thread.currentThread();
            LockSupport.park();
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            while (null == t3) {

            }
            LockSupport.unpark(t3);
        }

        @Override
        public void third(Runnable printThird) throws InterruptedException {
            t3 = Thread.currentThread();
            LockSupport.park();
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }

    /**
     * wait notify不推荐
     * 当执行的方法多了之后不知道什么时候适合的方法才获取到锁
     * LockSupport 和 join 好一点
     */
    private static class Foo4 implements Foo {

        private Object lock = new Object();
        private volatile int order = 1;

        @Override
        public void first(Runnable printFirst) throws InterruptedException {
            synchronized (lock) {
                // printFirst.run() outputs "first". Do not change or remove this line.
                printFirst.run();
                order = 2;
                lock.notifyAll();
            }

        }

        @Override
        public void second(Runnable printSecond) throws InterruptedException {
            synchronized (lock) {
                while (2 != order) {
                    lock.wait();
                }
                // printSecond.run() outputs "second". Do not change or remove this line.
                printSecond.run();
                order = 3;
                lock.notifyAll();
            }
        }

        @Override
        public void third(Runnable printThird) throws InterruptedException {
            synchronized (lock) {
                while (3 != order) {
                    lock.wait();
                }
                // printThird.run() outputs "third". Do not change or remove this line.
                printThird.run();
            }
        }
    }
}

