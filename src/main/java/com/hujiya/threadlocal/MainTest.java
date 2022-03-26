package com.hujiya.threadlocal;

public class MainTest {

    public static void main(String[] args) {
        Bank bank = new Bank();
      Object account = bank.account;

        Thread xMThread = new Thread(() -> bank.deposit(200), "小明");
        Thread xGThread = new Thread(() -> bank.deposit(200), "小刚");
        Thread xHThread = new Thread(() -> bank.deposit(200), "小红");
        xMThread.start();
//        xGThread.start();
//        xHThread.start();
    }
}

class Bank {
    // 初始化账户余额为 100
    protected static ThreadLocal<Integer> account = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 1000;
        }
    };

    public void deposit(int money) {
        try {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + "--当前账户余额为：" + account.get());
            account.set(account.get() + money);
            System.out.println(threadName + "--存入 " + money + " 后账户余额gc前为：" + account.get());
           int i = 0;
            while (++i>1000){
                byte[] bytes = new byte[100000];
            }
            System.out.println(Runtime.getRuntime().freeMemory());
            System.gc();
            System.out.println(Runtime.getRuntime().freeMemory());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + "--存入 " + money + " 后账户余额gc后为：" + account.get());

            System.out.println(Thread.currentThread().getThreadGroup());
        } finally {
            account.remove();
        }
    }
}