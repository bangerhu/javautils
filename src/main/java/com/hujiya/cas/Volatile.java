package com.hujiya.cas;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Volatile {

    boolean flag = true;


    public static void main(String[] args) {

        System.out.println(Thread.activeCount());

        Volatile a = new Volatile();
        new Thread(
                () -> {
                    System.out.println(a.flag);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    a.flag = false;
                    System.out.println(a.flag);
                }
                , String.valueOf("1")).start();

        new Thread(
                () -> {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                , "").start();


        new Thread(
                () -> {
                    while (a.flag) {
                        System.out.println("googd");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , Thread.currentThread().getName()).start();


//        while (Thread.activeCount()>2){
//
//        }
    }
}

class User {
    String name;
}