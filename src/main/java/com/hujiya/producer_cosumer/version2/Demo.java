package com.hujiya.producer_cosumer.version2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo {

    public static void main(String[] args) {
        Shop shop = new Shop();

        for (int i = 0; i < 5; i++) {
            new Thread(
                    () -> {
                        shop.decrement();
                    }
                    , "AAA" + String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(
                    () -> {
                        shop.increment();
                    }
                    , "BBB" + String.valueOf(i)).start();
        }

    }
}

class Shop {

    // 最少0个，最多3个
    private volatile int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();

    public void increment() {

        try {
            lock.lock();

            while (num >= 3) {
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("新增一个包子，现在包子数量：" + ++num);
            condition2.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void decrement() {

        try {
            lock.lock();

            while (num <= 0) {
                try {
                    condition2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("减少一个包子，现在包子数量：" + --num);
            condition1.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }
}