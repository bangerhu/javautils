package com.hujiya.volatile_;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo {

    static volatile int num = 0;

    static int plus(){
      return num++;
   }
   public static void main(String[] args) throws Exception{
//      VolatileDemo volatileDemo = new VolatileDemo();
      for (int i = 0; i < 1000000; i++) {
       new Thread(
                 () -> {
                    plus();
                 }
                 , Thread.currentThread().getName() + String.valueOf(i)).start();
      }

      while (Thread.activeCount()>2){
      }
      System.out.println(num);
   }


}
