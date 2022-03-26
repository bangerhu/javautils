package com.hujiya.uuid;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class UUIDDemo {


    public static void main(String[] args) {


        TimeBasedGenerator generator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());

        System.out.println(generator.generate().toString());
    }
}
