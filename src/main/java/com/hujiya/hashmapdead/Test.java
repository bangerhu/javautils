package com.hujiya.hashmapdead;

public class Test {

    public static void main(String[] args)
    {
        TestThread t0 = new TestThread();
        TestThread t1 = new TestThread();
        TestThread t2 = new TestThread();
        TestThread t3 = new TestThread();
        TestThread t4 = new TestThread();
        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t0.join();
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
