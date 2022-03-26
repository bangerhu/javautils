package com.hujiya.pecs;


public class Plat<T> {
    private T t;

    public void add(T t) {
        this.t = t;
    }

    public T get() {
        return this.t;
    }
}
