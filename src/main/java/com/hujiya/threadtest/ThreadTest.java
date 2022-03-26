package com.hujiya.threadtest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadTest {

    public static void main(String[] args) throws Exception{

//        for (int i = 0; i < 1; i++) {
//            new Thread(
//                    () -> {
//
////                        while (true){
////                            log.info("hello");
////                        }
//                    }
//                    , String.valueOf(i)).start();
//        }

        // 2 个线程，一个main线程，一个GC守护线程
        log.info(Thread.activeCount()+"");
        while (Thread.activeCount()>2){
            Thread.yield(); //yild  退缩
        }
        int i =0;
        System.out.println(i++);
        System.out.println(i);

        System.out.println("");

        System.out.println("212321");

        System.out.println("oosolsl");


        System.out.println("odooelel");


    }
}
