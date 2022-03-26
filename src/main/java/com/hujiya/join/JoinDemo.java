package com.hujiya.join;

import java.util.concurrent.TimeUnit;

public class JoinDemo {

    public static void main(String[] args) {
      Thread t1 =  new Thread(
                () -> {
                    try {
                        System.out.println("t11111");
                        try {
                            TimeUnit.SECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                , Thread.currentThread().getName());


        Thread t2 =  new Thread(
                () -> {
                    try {
                    t1.join();
                        System.out.println("t22222");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                , Thread.currentThread().getName());


        t1.start();

        t2.start();
    }
}
