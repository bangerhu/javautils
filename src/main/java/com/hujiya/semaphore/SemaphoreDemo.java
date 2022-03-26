package com.hujiya.semaphore;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {


    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            new Thread(
                    () -> {
                        try {
                            semaphore.acquire();
                            System.out.println(Thread.currentThread().getName()+"拿到车位");
                            try {
                                TimeUnit.SECONDS.sleep(3);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            semaphore.release();
                            System.out.println(Thread.currentThread().getName()+"释放车位");
                        }
                    }
                    , Thread.currentThread().getName() + String.valueOf(i)).start();
        }


    }
}
