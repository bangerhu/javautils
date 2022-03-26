package com.hujiya.interrupt;

import java.util.concurrent.TimeUnit;

/*
 一    终止线程的四种方式？
 *     1.程序运行结束，线程终止。
 *
 *     2.使用退出标志，退出线程。自定义条件。
 *             正常而言，线程执行完run方法，就会结束。但是有些线程是服务线程，需要长时间运行。
 *             可以在外部设置一个条件，满足条件时才关闭线程。
 *
 *  3.interrupt方法来中断线程，可以分为两种情况
 *        a 线程处于阻塞状态： 当线程使用了sleep    wait   socket中的receiver  ，accept等方法时。
 *          此时调用线程的interrupt方法，会抛出interruptException。
 *          阻塞中的方法抛出异常，通过代码捕获，然后break跳出循环状态，才能正常结束run方法。
 *
 *      b 线程是未阻塞状态， 使用isinterrupt 方法判断线程的中断标志来退出循环。
 *        使用interrupt方法，会把中断标志设置为true。
 *        和使用自定义标志来控制循环是一样的。
 *
 *    什么是中断？
 *        Java中用于停止线程的机制------中断。
 *        中断是一种协作机制，中断的过程需要程序员自己实现。调用interrupt方法也只是将标示设置为true。
 *        每个线程对象中都有一个标识，用于表示线程是否被中断；该标识位为true表示中断，为false表示未中断；
 *        通过调用线程对象的interrupt方法将该线程的标识位设为true；可以在别的线程中调用，也可以在自己的线程中调用。
 *
 *  为什么使用中断？
 *      在可能会发生的中断线程中，一直监听中断的状态，一旦发生了中断，就执行相关代码，进行处理。
 *      while（thread.isInterrupted()）{  //没有中断，继续执行
 *          xxxxxx
 *      }
 *
 *      // 发生中断了 需要的处理
 *          System.out.println("发生了中断，请处理。。。");
 *
 */
public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException  {
        ThreadIf thread = new ThreadIf();

        //isInterrupted  判断调用者线程的中断标志  false or  true ，验证状态。
        if (thread.isInterrupted()) {
            System.out.println("当前的状态是false，不会输出本条语句");
        }

        thread.start();

/*
         把中断的标志 设置为true ,通知线程应该中断了。
        此时线程是非阻塞状态，将会正常执行，不受影响。
 */
        thread.interrupt();

        //再次验证，此时已经中断 条件是true 可以输出语句。  使用while 就可以一直执行，和使用自定义的条件，是一样的效果。
        if (thread.isInterrupted()) {
            System.out.println("isInterrupted  判断调用者线程的中断标志  false or  true ，验证状态。 标识是true，输出本条语句");
        }

        //interrupted 方法有两个作用。会返回当前的中断状态，并且清除中断状态。 会把true，变为false

    }
}

class ThreadIf extends Thread{
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}