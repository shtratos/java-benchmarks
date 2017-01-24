package com.github.shtratos.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;

/**
 * Tests {@code new} operator vs {@link Class#newInstance()} call.
 * <p>
 * sample results:
 * <pre>
 *     # Run complete. Total time: 00:13:28
 *
 *     Benchmark                                 Mode  Cnt          Score         Error  Units
 *     NewInstanceBenchmark.measureNew          thrpt  200  208192295.760 ± 2368570.262  ops/s
 *     NewInstanceBenchmark.measureNewInstance  thrpt  200  128489499.276 ± 1781447.718  ops/s
 *
 * </pre>
 */
public class NewInstanceBenchmark {


    @Benchmark
    public ArrayList<Object> measureNew() {
        return new ArrayList<>();
    }

    @Benchmark
    public ArrayList measureNewInstance() throws IllegalAccessException, InstantiationException {
        return ArrayList.class.newInstance();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(NewInstanceBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(5)
                .threads(6)
                .mode(Mode.Throughput)
                .build();

        new Runner(opt).run();
    }

}
