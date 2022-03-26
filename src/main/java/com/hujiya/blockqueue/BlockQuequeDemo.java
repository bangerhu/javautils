package com.hujiya.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockQuequeDemo {


    public static void main(String[] args) throws Exception{

        BlockingQueue<String> b = new ArrayBlockingQueue<String>(3);

        System.out.println(        b.remove("abd"));

    }
}
