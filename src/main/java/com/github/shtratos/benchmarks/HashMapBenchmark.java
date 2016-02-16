package com.github.shtratos.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class HashMapBenchmark {

    private static final int PROPERTIES_IN_ENTITY = 252;
    private static final int KEY_SIZE = 1000;
    private static final int VALUE_SIZE = 1000;

    private static Map<String, String> generateProperties() {
        Map<String, String> properties = new HashMap<>();
        for (int i = 0; i < PROPERTIES_IN_ENTITY; i++) {
            properties.put(randomAlphanumeric(KEY_SIZE), randomAlphanumeric(VALUE_SIZE));
        }
        return properties;
    }

    private static final Map<String, String> TEST_PROPERTIES = generateProperties();

    private static final HashMap<String, String> HASH_MAP = new HashMap<>(TEST_PROPERTIES);
    private static final TreeMap<String, String> TREE_MAP = new TreeMap<>(TEST_PROPERTIES);

    static class Entity {
        private HashMap<String, String> properties = new HashMap<>();

        public HashMap<String, String> getProperties() {
            return properties;
        }

        public void setProperties(HashMap<String, String> properties) {
            this.properties = properties;
        }

        public void copyProperties(Map<String, String> properties) {
            this.properties = new HashMap<>(properties);
        }

        public void smartCopyProperties(Map<String, String> properties) {
            if (properties instanceof HashMap) {
                this.properties = (HashMap<String, String>) properties;
            } else {
                this.properties = new HashMap<>(properties);
            }
        }
    }

    private static final Entity entity = new Entity();

    @Benchmark
    public void measureAssignment() {
        entity.setProperties(HASH_MAP);
    }

    @Benchmark
    public void measureHashMapCopy() {
        entity.copyProperties(HASH_MAP);
    }

    @Benchmark
    public void measureTreeMapCopy() {
        entity.copyProperties(TREE_MAP);
    }

    @Benchmark
    public void measureSmartCopyGoodCase() {
        entity.smartCopyProperties(HASH_MAP);
    }

    @Benchmark
    public void measureSmartCopyBadCase() {
        entity.smartCopyProperties(TREE_MAP);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(HashMapBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(5)
                .threads(6)
                .mode(Mode.Throughput)
                .build();

        new Runner(opt).run();
    }
}
