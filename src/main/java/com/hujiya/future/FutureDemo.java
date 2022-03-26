package com.hujiya.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureDemo {



    public static void main(String[] args) throws Exception{
        Callable caller = new Caller();
        Runnable task = new FutureTask(caller);
        Thread thread = new Thread(task);
        thread.start();
        System.out.println(((FutureTask) task).get());
    }
}

class Caller implements Callable{

    @Override
    public Object call() throws Exception {
        System.out.println("计算最终结果");
        return 100;
    }
}