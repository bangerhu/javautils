package com.hujiya.trybase;

public class FunctionalInterfaceDemo {

    @FunctionalInterface
    public interface TestHU{
        public String abc();
    }

    public static void main(String[] args) {
        TestHU a = ()-> {
            System.out.println("ddddd");
            return "2323424";
        };
        System.out.println(a);
        System.out.println(a.abc());
    }
}
