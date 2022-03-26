package com.hujiya.hashmapdead;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThread extends Thread {
    static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(1);
    static AtomicInteger at = new AtomicInteger();

    @Override
    public void run() {
        while (at.get() < 10000000) {
            map.put(at.get(), at.get());
            at.incrementAndGet();
        }
    }
}