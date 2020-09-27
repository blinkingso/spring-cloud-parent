package com.yz.chapter01

import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 * Java中规定：当所有的非守护线程退出后， 整个JVM进程就会退出
 * setDaemon
 */
fun t1() {
    thread(start = true, isDaemon = true, name = "守护线程") {
        while (true) {
            TimeUnit.SECONDS.sleep(1)
        }
    }
}

fun t2() {
    val stopped = false
    val lock = "abc"
    val t = thread(start = true, isDaemon = false, name = "测试线程") {
        while (!stopped) {
            synchronized(lock) {
                val a = 1
                val b = 2
                println(a + b)
                println("Thread is executing")

                // 线程t在interrupt方法在此行执行时被中断
                TimeUnit.SECONDS.sleep(2)

                println("Thread.interrupted: " + Thread.interrupted())

                // interrupted()方法执行后会重置中断状态
                println("线程继续执行喽：" + Thread.currentThread().isInterrupted)
            }
        }
    }

//    TimeUnit.SECONDS.sleep(1)
//    t.interrupt()
    println("isInterrupted:" + t.isInterrupted)
}

class A {
    private var X: Int = 0
    private var a: Int = 0
    private var Y: Int = 0
    private var b: Int = 0

    fun a() {
        X = 1
        a = Y
    }

    fun b() {
        Y = 1
        b = X
    }

    fun test() {
        val t1 = thread(name = "aThread", start = true, isDaemon = false) { a() }
        val t2 = thread(name = "bThread", start = true, isDaemon = false) { b() }

        t1.join()
        t2.join()
        println("a=$a, b=$b")
    }
}

fun happensBefore() {
    class B {
        private var a: Int = 0
        private var c: Int = 0


        @Synchronized
        fun set() {
            a = 5
            c = 1
        }

        @Synchronized
        fun get(): Int {
            return a
        }
    }

    B().apply {
        thread { println("a = ${this.get()}") }
        thread { this.set() }
        thread { println("a = ${this.get()}") }
    }
}

fun main() {
//    t2()
//    repeat(100) {
//        A().apply {
//            this.test()
//        }
//    }

    happensBefore()
    println("===================")
}