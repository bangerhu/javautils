package com.hujiya.producer_cosumer.version3;

import java.util.concurrent.TimeUnit;

/**
 * 任务入口
 */
public class StartHere {

    public static void main(String[] args) {
        // 这家店最多可以容纳5桌人吃包子
        BaoziShop shop = new BaoziShop(5);

        Chushi chushi = new Chushi(shop);
        // 厨师开始上班
        chushi.start();
        // 店里总共有一次性桌子10个
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(
                    () -> {
                        shop.increment(new Zhuozi(finalI));
                    }
                    , "AAA" + String.valueOf(i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 厨师开始下班
        chushi.down();
    }
}
