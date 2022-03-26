package com.hujiya.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

@Slf4j
public class Test {

    private static AtomicStampedReference atomicInt = new AtomicStampedReference(100, 0);

    public static void main(String[] args) throws InterruptedException {
        Thread intT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicInt.getStamp();
                log.info("1:" + stamp+":"+atomicInt.getReference());
                atomicInt.compareAndSet(100, 101, stamp, stamp + 1);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stamp = atomicInt.getStamp();
                log.info("2:" + stamp+":"+atomicInt.getReference());
                atomicInt.compareAndSet(101, 100, stamp, stamp + 1);
                stamp = atomicInt.getStamp();
                log.info("3:" + stamp+":"+atomicInt.getReference());
            }
        });

        Thread intT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean c3 = false;
                while (!c3) {
                    Integer ref =(Integer) atomicInt.getReference();
                    int stamp = atomicInt.getStamp();
                    try {
                        log.info("任务开始:" + stamp + ":" + atomicInt.getReference());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                    }
                     c3 = atomicInt.compareAndSet(ref, ref+1, stamp, stamp + 1);
                    log.info("任务结束:" + stamp + ":" + atomicInt.getReference() + ":" + c3);
                }
            }
        });

        intT1.start();
        intT2.start();
        intT1.join();
        intT2.join();
    }
}
