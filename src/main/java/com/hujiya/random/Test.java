package com.hujiya.random;

import java.util.concurrent.ThreadLocalRandom;

public class Test {

    public static void main(String[] args) {


        for (int i = 0; i < 100; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    ThreadLocalRandom random = ThreadLocalRandom.current();
                    System.out.println(random.nextInt(100));
                }
            };
            t.start();


        }
    }
}
