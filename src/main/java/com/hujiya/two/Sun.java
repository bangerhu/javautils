package com.hujiya.two;

public class Sun implements Cloneable {

    protected Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static synchronized void say(Object s) {
        extractM(s);
    }

    private static void extractM(Object s) {
        System.out.println("开始" + s);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("结束" + s);
    }
}
