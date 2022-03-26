package com.hujiya.threadtest;

import java.util.concurrent.TimeUnit;

public class ThreadJoin {


    public static void main(String[] args) throws Exception{

      Thread t1 = new Thread(
                () -> {
                    try {
                        System.out.println("T1开始");
                        try {
                            try {
                                TimeUnit.SECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("T1结束");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                , Thread.currentThread().getName());


        Thread t2 = new Thread(
                () -> {
                    try {
                        System.out.println("T2开始");
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("T2结束");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                , Thread.currentThread().getName());

        t1.start();
        t1.join();
        t2.start();

    }
}
