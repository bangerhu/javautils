package com.hujiya.gc;

import java.util.concurrent.TimeUnit;

public class GCDemo {

    public static void main(String[] args) {
        System.out.println("****** hello GC");

        while (true){
            new Thread(
                    () -> {
                        try {

                            try {
                                TimeUnit.HOURS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    , Thread.currentThread().getName()).start();
        }
    }
}
