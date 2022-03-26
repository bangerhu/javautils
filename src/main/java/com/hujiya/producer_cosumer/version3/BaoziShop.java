package com.hujiya.producer_cosumer.version3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BaoziShop {

    public BaoziShop(int max) {
        this.max = max;
        this.zhuoziList = new CopyOnWriteArrayList(new ArrayList(max));
    }

    // 最少0个，最多3个
    private int max;
    private volatile int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    CopyOnWriteArrayList<Zhuozi> zhuoziList;

    public void increment(Zhuozi zhuozi) {
        try {
            lock.lock();

            while (num >= max) {
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            zhuoziList.add(zhuozi);
            // 对桌子按优先级排序
            Collections.sort(zhuoziList);
            System.out.println("新增一个桌子，现在桌子数量：" + ++num);
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void decrement(Zhuozi zhuozi) {
        try {
            lock.lock();

            while (num <= 0) {
                try {
                    condition2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            zhuoziList.remove(zhuozi);
            Collections.sort(zhuoziList);

            System.out.println("减少一个桌子，现在桌子数量：" + --num);
            condition1.signal();

            if (num == 0) {
                System.out.println("店里桌子都空着");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
