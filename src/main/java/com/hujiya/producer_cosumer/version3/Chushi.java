package com.hujiya.producer_cosumer.version3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 厨师定时生产包子类
 */
public class Chushi extends Thread {

    public Chushi(BaoziShop baoziShop) {
        this.baoziShop = baoziShop;
    }

    volatile boolean working = true;
    private List<Baozi> baoziList = new ArrayList<>();
    private BaoziShop baoziShop;

    public void run() {
        while (this.working) {
            System.out.println("厨师刚做了一个包子");
            makeBaozi(baoziShop);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void down() {
        this.working = false;
        System.out.println("厨师停止做包子");
    }

    public void makeBaozi(BaoziShop baoziShop) {
        baoziList.add(new Baozi());
        if (null != baoziShop.zhuoziList && baoziShop.zhuoziList.size() > 0) {
            baoziShop.zhuoziList.forEach(zhuoZi -> {
                // 优先级最高的先执行
                if (zhuoZi.resourceNum < baoziList.size()) {
                    baoziShop.decrement(zhuoZi);
                    for (int i = zhuoZi.resourceNum; i >= 0; i--) {
                        baoziList.remove(i);
                    }
                }
            });
        }
        System.out.println("目前还剩包子" + baoziList.size());
    }

    public Chushi() {
    }
}
