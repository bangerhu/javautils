package com.hujiya.reffer;

import java.lang.ref.WeakReference;

public class ReffereDemo {

    public static void main(String[] args) {
        Integer i = new Integer(13883);
        WeakReference<Integer> r = new WeakReference<>(i);
        System.out.println(r.get());
//        i = null;
        System.gc();
        System.out.println(r.get());
    }
}
