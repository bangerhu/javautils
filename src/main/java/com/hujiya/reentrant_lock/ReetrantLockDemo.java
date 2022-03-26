package com.hujiya.reentrant_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReetrantLockDemo {

    AtomicInteger atomicReference = new AtomicInteger(3);

    public void lock() {
        System.out.println(Thread.currentThread().getName());
        while (!atomicReference.compareAndSet(3, 4)) {
            // waiting
        }
    }

    public void unLock() {
        System.out.println(Thread.currentThread().getName());
        atomicReference.compareAndSet(4, 3);
    }

    public static void main(String[] args) {
        ReetrantLockDemo demo = new ReetrantLockDemo();
        new Thread(
                () -> {
                    try {
                        demo.lock();

                        try {
                            TimeUnit.SECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        demo.unLock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                , "AAAA").start();


        new Thread(
                () -> {
                    try {
                        demo.lock();

                        demo.unLock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                , "BBB").start();


        Lock lock = null;
        try {
            lock.lock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        ReentrantLock lock1= new ReentrantLock();

    }
}


