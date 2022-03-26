package com.hujiya.clone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        String[] str = new String[] { "you", "wu" };
        List list = Arrays.asList(str);
        list.add("abadc");
        System.out.println(list);
    }
}
