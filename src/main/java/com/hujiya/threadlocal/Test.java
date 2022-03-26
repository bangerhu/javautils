package com.hujiya.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) throws Exception {

        // FIXME hujiya
        // TODO hujiya
        /// eeerrr
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    Integer before = threadLocal.get();
                    threadLocal.set(before + 1);
                    Integer after = threadLocal.get();

                    System.out.println(Thread.currentThread().getName() + "before: " + before + ",after: " + after);
                } finally {
                    threadLocal.remove();
                }
            });
        }
        executorService.shutdown();
    }
}
