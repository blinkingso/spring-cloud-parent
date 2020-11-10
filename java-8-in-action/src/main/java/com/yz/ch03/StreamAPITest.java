package com.yz.ch03;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream API 使用
 */
public class StreamAPITest {

    public static void main(String[] args) {
//        stream();
        System.out.println(add(Stream.of(1, 2, 3, 4, 5)));
        var h1 = new HashMap<String, String>();
        h1.put("name", "h2");
        h1.put("country", "china");
        artist(List.of(h1));

        System.out.println(String.join(",", map(List.of("a", "b", "c"), s -> s + "_end")));

        System.out.println(String.join(",", filter(List.of("aa", "abc", "csa", "mba"),
                s -> s.startsWith("a"))));
    }

    // reduce 实现filter功能
    private static <T> List<T> filter(List<T> data, Predicate<T> predicate) {
        return data.stream().reduce(new ArrayList<>(),
                (list, t) -> {
                    if (predicate.test(t)) {
                        list.add(t);
                    }

                    return list;
                },
                (result, predicates) -> {
                    result.addAll(predicates);
                    return result;
                });
    }

    // reduce 方法实现map功能
    private static <T, R> List<R> map(List<T> data, Function<T, R> mapFunc) {
        // 使用reduce实现map功能
        return data.stream().reduce(new ArrayList<>(),
                (objects, t) -> {
                    R r = mapFunc.apply(t);
                    objects.add(r);
                    return objects;
                },
                (objects, objects2) -> {
                    objects.addAll(objects2);
                    return objects;
                });
    }

    private static List<String> artist(List<Map<String, String>> artist) {
        return artist.stream().map(map -> map.get("name") + map.get("country"))
                .collect(Collectors.toList());
    }

    private static int add(Stream<Integer> numbers) {
        return numbers.reduce(0, Integer::sum);
    }

    /**
     * 实现机制：
     * 整个过程被分解为两种更简单的操作：
     * 过滤和计数，看似有化简为繁之嫌
     */
    public static void stream() {
        List<String> list = List.of("a", "b", "v");
        var count = list.stream()
                .filter(it -> it.startsWith("ac"))
                .count();
        System.out.println(count);

        List<String> collected = Stream.of("a", "b", "c")
                .collect(Collectors.toList());
        assert Arrays.asList("a", "b", "c").equals(collected);

        // map
        // 转化为大写
        collected = collected.stream().map(it -> it.toUpperCase())
                .collect(Collectors.toList());
        // filter
        collected = collected.stream()
                .filter(it -> it.startsWith("a"))
                .collect(Collectors.toList());
        // flatMap 操作
        // 用户希望让map操作有点变化， 生成一个新的Stream对象取而代之。
        // 用户通常不希望结果是一连串的流，此时flatMap最能排上用场
        // flatMap将collections中的collectors保存在一个Stream中。
        var data = Stream.of(Arrays.asList(1, 2, 4), Arrays.asList(3, 4, 5))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println(data);
        // max 和 min
        List<Track> tracks = Stream.of(
                new Track("Ba kai", 124),
                new Track("Violet for you", 531),
                new Track("Time Was", 214))
                .collect(Collectors.toList());
        var minTrack =
                tracks.stream().min(Comparator.comparing(track -> track.length))
                        .get();
        System.out.println(minTrack);
        var maxTrack = tracks.stream()
                .max(Comparator.comparing(track -> track.length))
                .orElseGet(() -> new Track("DefaultMax", 9999));
        System.out.println(maxTrack);

        // reduce operator
        var custom = tracks.stream()
                .reduce((max, element) -> {
                    if (max.getLength().compareTo(element.getLength()) > 0) {
                        return max;
                    } else return element;
                }).get();
        System.out.println("custom-reduce ==> " + custom);

        // reduce sum
        var sum = tracks.stream()
                .reduce(0, (acc, track) -> acc + track.getLength(),
                        Integer::sum);
        System.out.println("sum = " + sum);
    }

    private static class Track {
        public Track(String name, Integer length) {
            this.name = name;
            this.length = length;
        }

        private String name;
        private Integer length;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        @Override
        public String toString() {
            return "Track{" +
                    "name='" + name + '\'' +
                    ", length=" + length +
                    '}';
        }
    }
}