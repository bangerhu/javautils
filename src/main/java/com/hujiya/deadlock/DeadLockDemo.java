package com.hujiya.deadlock;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo implements Runnable{

    String lock1 = "";
    String lock2 = "";

    public DeadLockDemo(String lock1, String lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }
    @Override
    public void run() {
        synchronized (lock1){

            System.out.println(lock1+"开始等待，"+lock2);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2){

                System.out.println("开始，"+lock2);
            }
        }
    }

    public static void main(String[] args) {

        new Thread(new DeadLockDemo("lock1","lock2"), "AAAA").start();
        new Thread(new DeadLockDemo("lock2","lock1"), "BBBB").start();
    }


}
