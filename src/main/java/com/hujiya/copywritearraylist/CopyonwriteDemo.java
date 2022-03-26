package com.hujiya.copywritearraylist;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyonwriteDemo {

    public static void main(String[] args) {

        System.out.println(Thread.activeCount());
        CopyOnWriteArrayList<Integer> a = new CopyOnWriteArrayList();
        for (int i = 0; i < 10000; i++) {
            new Thread(
                    () -> {
                        a.add(123);
                    }
                    , Thread.currentThread().getName() + String.valueOf(i)).start();
        }

        while (Thread.activeCount()>2){

        }

        System.out.println(a.size());
    }
}
