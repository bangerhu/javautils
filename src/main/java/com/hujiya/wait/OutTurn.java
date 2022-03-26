package com.hujiya.wait;

import java.util.concurrent.TimeUnit;

class OutTurn {
    private boolean isSub = true;
    private int count = 0;

    public  void sub() {
        try {
            TimeUnit.MILLISECONDS.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized(this) {
            try {
                while (!isSub) {
                    this.wait();
                }
                System.out.println("sub ---- " + count);
                isSub = false;
                this.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
            count++;
        }


    }

    public  void main() {
        try {
            TimeUnit.MILLISECONDS.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized(this) {
            try {
                while (isSub) {
                    this.wait();
                }
                System.out.println("main (((((((((((( " + count);
                isSub = true;
                this.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
            count++;
        }
    }
}