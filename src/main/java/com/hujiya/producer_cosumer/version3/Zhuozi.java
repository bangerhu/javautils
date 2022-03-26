package com.hujiya.producer_cosumer.version3;

import java.util.Random;

/**
 * 一次性桌子类
 */
public class Zhuozi implements Comparable<Zhuozi> {

    //  顾客姓名
    String name;
    //  顾客优先级
    int priority;
    //  顾客点了几个包子
    int resourceNum;

    public Zhuozi(int no) {
        this.name = "第" + no + "张桌子";
        this.priority = new Random().nextInt(3);
        this.resourceNum = new Random().nextInt(3);
        ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getResourceNum() {
        return resourceNum;
    }

    public void setResourceNum(int resourceNum) {
        this.resourceNum = resourceNum;
    }

    @Override
    public int compareTo(Zhuozi o) {
        return this.priority - o.getPriority();
    }
}
