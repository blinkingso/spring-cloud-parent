package com.yz.ch02;

/**
 * lambda 表达式是一个匿名方法， 将行为像数据
 * 一样进行传递
 * <p>
 * 函数接口指具有单个抽象方法的接口， 用来表示Lambda表达式的类型
 */
public class FunctionalInterfacesTest {
    public static void main(String[] args) {
        BinaryOperator<Long> addLongs = (x, y) -> x + y;
        Function<Long, Long> fun = x -> x + 1L;
        System.out.println(fun.apply(123L));

        // ThreadLocal
        ThreadLocal<Long> longThreadLocal = ThreadLocal.withInitial(() -> 10L);
        System.out.println(longThreadLocal.get());
    }
}

/**
 * x -> x + 1
 *
 * @param <T>
 * @param <R>
 */
interface Function<T, R> {
    R apply(T t);
}

interface Supplier<T> {
    T supplier();
}

interface Consumer<T> {
    void consume(T t);
}

interface Predicate<T> {
    boolean predicate(T t);
}

interface UnaryOperator<T> {
    T unary(T t);
}

interface BinaryOperator<T> {
    T binary(T t1, T t2);
}
