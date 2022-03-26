package com.hujiya.jmh;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class HelloworldRunner {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include("Helloworld")
                .exclude("Pref")
                .warmupIterations(10)
                .measurementIterations(10)
                .forks(3)
                .build();

        new Runner(opt).run();
    }
}
