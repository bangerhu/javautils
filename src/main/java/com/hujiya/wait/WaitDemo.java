package com.hujiya.wait;

public class WaitDemo {

    public static void main(String[] args) {
        // System.out.println("lock");

        final OutTurn ot = new OutTurn();

        for (int j = 0; j < 100; j++) {

            new Thread(new Runnable() {

                public void run() {
                    for (int i = 0; i < 5; i++) {
                        ot.sub();
                    }
                }
            }).start();

            new Thread(new Runnable() {

                public void run() {
                    for (int i = 0; i < 5; i++) {
                        ot.main();
                    }
                }
            }).start();
        }

    }
}
