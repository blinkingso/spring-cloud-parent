package com.yz.ch04;

import java.util.logging.Logger;
import java.util.stream.Stream;

public class LibTest {

    public static void main(String[] args) {
        logger();
        statistics();
    }

    private static void logger() {
        Logger logger = Logger.getLogger(LibTest.class.getName());
        logger.info(() -> "hello");
    }

    // 基本类型
    private static void statistics() {
        // 统计长度
        var summary = Stream
                .of(new Album(123,"one"),
                        new Album(238, "two"))
                .mapToInt(a -> a.getLength())
                .summaryStatistics();
        System.out.printf("Max: %d, Min: %d, Ave: %f, Sum: %d",
                summary.getMax(),
                summary.getMin(),
                summary.getAverage(),
                summary.getSum());
    }

    static class Album {
        private int length;
        private String name;

        public Album(int length, String name) {
            this.length = length;
            this.name = name;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
