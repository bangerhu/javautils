package com.hujiya.producer_cosumer;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Demo {

    public static void main(String[] args) throws InterruptedException {


        BlockingQueue blockingQueue = new ArrayBlockingQueue(3);

        System.out.println(blockingQueue.size());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.size());
    }
}
