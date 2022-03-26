package com.hujiya.cas.aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Test {

    public static void main(String[] args) {

        String str1 = "aaa";
        String str2 = "bbb";
        AtomicStampedReference<String> reference = new AtomicStampedReference<String>(str1,1);
        reference.compareAndSet(str1,str2,reference.getStamp(),reference.getStamp()+1);
        System.out.println("reference.getReference() = " + reference.getReference());

        boolean b = reference.attemptStamp(str2, reference.getStamp() + 1);
        System.out.println("b: "+b);
        System.out.println("reference.getStamp() = "+reference.getStamp());

        boolean c = reference.weakCompareAndSet(str2,"ccc",4, reference.getStamp()+1);
        System.out.println("reference.getReference() = "+reference.getReference());
        System.out.println("c = " + c);

        try {
            TimeUnit.SECONDS.sleep(1);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private AtomicStampedReference<Integer> count = new AtomicStampedReference<Integer>(0, 0);

    public int getCount() {
        return count.getReference();
    }

    public int increment() {
        while (true) {
            //必须先获取stamp，然后取值，顺序不能反，否则仍然会有ABA的问题
            int stamp = count.getStamp();
            System.out.println(stamp);
            Integer value = count.getReference();
            System.out.println(value);
            int newValue = value + 1;
            System.out.println(newValue);
            boolean writeOk = count.compareAndSet(value, newValue, stamp, stamp + 1);
            System.out.println(writeOk);
            if (writeOk) {
                return newValue;
            }
        }
    }

    public int decrement() {
        while (true) {
            //必须先获取stamp，然后取值，顺序不能反，否则仍然会有ABA的问题
            int stamp = count.getStamp();
            System.out.println(stamp);
            Integer value = count.getReference();
            System.out.println(value);
            int newValue = value - 1;
            boolean writeOk = count.compareAndSet(value, newValue, stamp, stamp + 1);
            if (writeOk) {
                return newValue;
            }
        }
    }

}
