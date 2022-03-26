package com.hujiya.locksupport;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class ParkDemo {


    public static void main(String[] args) {
        Thread t =
        new Thread(
                () -> {
                    try {
                        System.out.println("111111");
                        LockSupport.park();
                        System.out.println("22222");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                , Thread.currentThread().getName());
        t.start();
        System.out.println("33333333");
        LockSupport.unpark(t);
    Byte[] bytes = new Byte[(int) Integer.MAX_VALUE];
    }
}
