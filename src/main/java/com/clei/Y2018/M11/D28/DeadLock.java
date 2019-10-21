package com.clei.Y2018.M11.D28;

public class DeadLock {
    public static void main(String[] args) {
        String s1="老大";
        String s2="老二";
        MyThread t1=new MyThread("张三",s1,s2);
        MyThread1 t2=new MyThread1("李四",s1,s2);
        t1.start();
        t2.start();
    }
}

class MyThread extends Thread{
    private String obj1;
    private String obj2;
    public MyThread(){
        super();
    }
    public MyThread(String name,String obj1,String obj2){
        super(name);
        this.obj1 = obj1;
        this.obj2 = obj2;
    }
    @Override
    public void run(){
        System.out.println("进程"+this.getName()+"运行中...");
        synchronized (obj1){
            System.out.println("获得obj1");
            System.out.println("等待获取obj2....................");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj2){
                System.out.println("获得obj1");
            }
        }
        System.out.println("进程"+this.getName()+"结束");
    }
}

class MyThread1 extends Thread{
    private String obj1;
    private String obj2;
    public MyThread1(){
        super();
    }
    public MyThread1(String name,String obj1,String obj2){
        super(name);
        this.obj1 = obj1;
        this.obj2 = obj2;
    }
    @Override
    public void run(){
        System.out.println("进程"+this.getName()+"运行中...");
        synchronized (obj2){
            System.out.println("获得obj2");
            System.out.println("等待获取obj1....................");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj1){
                System.out.println("获得obj2");
            }
        }
        System.out.println("进程"+this.getName()+"结束");
    }
}
