package com.github.shtratos.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class CodepointsCountBenchmark {

    // thanks https://stackoverflow.com/a/27690536/7417402
    private static String repeat(String str, int times) {
        return Stream.generate(() -> str).limit(times).collect(joining());
    }

    public static final String TEST_STRING = "faklshfkjhsdkfjhksadjh##€^^∆¬˚∆¬2oihjhkasjRIP\uD83D\uDE41wsdjklah\uD83E\uDD36\uD83C\uDFFC";
    public static final String TEST_STRING_10 = repeat(TEST_STRING, 10);
    public static final String TEST_STRING_100 = repeat(TEST_STRING, 100);

    @Benchmark
    public int measureCodepointsCountMethod() {
        return TEST_STRING.codePointCount(0, TEST_STRING.length());
    }

    @Benchmark
    public long measureCodepointsCountStream() {
        return TEST_STRING.codePoints().count();
    }

    @Benchmark
    public int measureCodepointsCountMethod10() {
        return TEST_STRING_10.codePointCount(0, TEST_STRING_10.length());
    }

    @Benchmark
    public long measureCodepointsCountStream10() {
        return TEST_STRING_10.codePoints().count();
    }

    @Benchmark
    public int measureCodepointsCountMethod100() {
        return TEST_STRING_100.codePointCount(0, TEST_STRING_100.length());
    }

    @Benchmark
    public long measureCodepointsCountStream100() {
        return TEST_STRING_100.codePoints().count();
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CodepointsCountBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(5)
                .threads(6)
                .mode(Mode.Throughput)
                .build();

        new Runner(opt).run();
    }

    /**
     * # JMH version: 1.21
     * # VM version: JDK 10.0.1, Java HotSpot(TM) 64-Bit Server VM, 10.0.1+10
     * # VM invoker: /Library/Java/JavaVirtualMachines/jdk-10.0.1.jdk/Contents/Home/bin/java
     * # VM options: -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=50688:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8
     * # Warmup: 5 iterations, 10 s each
     * # Measurement: 5 iterations, 10 s each
     * # Timeout: 10 min per iteration
     * # Threads: 6 threads, will synchronize iterations
     * # Benchmark mode: Throughput, ops/time
     * Benchmark                                               Mode  Cnt         Score         Error  Units
     * CodepointsCountBenchmark.measureCodepointsCountMethod  thrpt   25  76676480.150 ± 2906603.942  ops/s
     * CodepointsCountBenchmark.measureCodepointsCountStream  thrpt   25  19492277.790 ±  299989.102  ops/s
     *
     *
     *
     * # JMH version: 1.21
     * # VM version: JDK 1.8.0_152, Java HotSpot(TM) 64-Bit Server VM, 25.152-b16
     * # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/jre/bin/java
     * # VM options: -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=50829:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8
     * # Warmup: 5 iterations, 10 s each
     * # Measurement: 5 iterations, 10 s each
     * # Timeout: 10 min per iteration
     * # Threads: 6 threads, will synchronize iterations
     * # Benchmark mode: Throughput, ops/time
     * Benchmark                                                  Mode  Cnt         Score         Error  Units
     * CodepointsCountBenchmark.measureCodepointsCountMethod     thrpt   25  75954548.640 ± 2381997.608  ops/s
     * CodepointsCountBenchmark.measureCodepointsCountMethod10   thrpt   25   7524309.712 ±  261800.254  ops/s
     * CodepointsCountBenchmark.measureCodepointsCountMethod100  thrpt   25    757574.854 ±   20199.463  ops/s
     * CodepointsCountBenchmark.measureCodepointsCountStream     thrpt   25  21092152.949 ±  590393.878  ops/s
     * CodepointsCountBenchmark.measureCodepointsCountStream10   thrpt   25   2696345.209 ±  101794.337  ops/s
     * CodepointsCountBenchmark.measureCodepointsCountStream100  thrpt   25    299081.815 ±    7164.375  ops/s
     */
}
