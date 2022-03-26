package com.hujiya.producer_cosumer.version1;

import java.util.concurrent.TimeUnit;

public class Demo {

    public static void main(String[] args) {
        Shop shop = new Shop();

        for (int i = 0; i < 5; i++) {

            new Thread(
                    () -> {
                        shop.decrement();
                    }
                    , "BBB" + String.valueOf(i)).start();

            new Thread(
                    () -> {
                        shop.increment();
                    }
                    , "AAA" + String.valueOf(i)).start();
        }

    }
}

class Shop {

    // 最少0个，最多3个
    private volatile int num = 0;

    public void increment() {
        synchronized (this) {
            while (num >= 3) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("新增一个包子，现在包子数量：" + ++num);
        }
        this.notifyAll();
    }


    public synchronized void decrement() {
        while (num <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("减少一个包子，现在包子数量：" + --num);
        this.notifyAll();
    }
}