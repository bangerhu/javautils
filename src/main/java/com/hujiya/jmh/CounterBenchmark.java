package com.hujiya.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
public class CounterBenchmark {

    @Benchmark
    @Group("Adder")
    @GroupThreads(60)
    public void incAdder(AdderCounterState state) {
        state.counter.increment();
    }

    @Benchmark
    @Group("Adder")
    @GroupThreads(40)
    public long getAdder(AdderCounterState state) {
        return state.counter.getCount();
    }

    @State(Scope.Group)
    public static class AdderCounterState {
        AdderCounter counter = new AdderCounter();
    }

    public static void main(String[] args) throws RunnerException {
        // 启动基准测试
        Options opt = new OptionsBuilder()
                .include(CounterBenchmark.class.getSimpleName()) // 要导入的测试类
                .build();
        new Runner(opt).run(); // 执行测试
    }
}
