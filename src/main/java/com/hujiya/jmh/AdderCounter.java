package com.hujiya.jmh;

import java.util.concurrent.atomic.LongAdder;

public class AdderCounter {
    private final LongAdder adder = new LongAdder();

    public long getCount() {
        return adder.longValue();
    }

    public void increment() {
        adder.increment();
    }
}