package com.hujiya.clone;

/**
 * Created by xy on 2017/12/25.
 */
public class Person implements Cloneable {
    private String name;
    private int age;
    private int[] ints;

    public int[] getInts() {
        return ints;
    }

    public Person(String name, int age, int[] ints) {
        this.name = name;
        this.age = age;
        this.ints = ints;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * 默认实现
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
