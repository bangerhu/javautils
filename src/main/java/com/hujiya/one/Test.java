package com.hujiya.one;


import com.hujiya.two.Sun;

public class Test {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(
                    () -> {
                        Sun.say(finalI);
                    }
            ).start();
        }


        for (int i = 0; i < 10; i++) {
            new Thread(
                    () -> {

                    }
                    , String.valueOf(i)).start();
        }

    }

}
