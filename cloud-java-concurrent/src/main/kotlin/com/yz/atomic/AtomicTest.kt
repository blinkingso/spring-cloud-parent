package com.yz.atomic

import sun.misc.Unsafe
import java.util.concurrent.atomic.AtomicIntegerArray
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import java.util.concurrent.atomic.AtomicStampedReference

class MyAtomicInt(@Volatile private var value: Int) {


    fun compareAndSet(expected: Int, newValue: Int): Boolean {
        return unsafe.compareAndSwapInt(this, valueOffset, expected, newValue)
    }

    fun getAndIncrement(): Int {
        val int = unsafe.getAndAddInt(this, valueOffset, 1)
        println("getAndIncrement is :$int")
        return int
    }

    fun get(): Int {
        return unsafe.getIntVolatile(this, valueOffset)
    }

    companion object {
        private val unsafe = getUnsafe()

        private val valueOffset: Long = unsafe.objectFieldOffset(MyAtomicInt::class.java.getDeclaredField("value"))

        private fun getUnsafe(): Unsafe {
            val field = Unsafe::class.java.getDeclaredField("theUnsafe")
            field.isAccessible = true
            return field.get(null) as Unsafe
        }
    }
}

open class M(@Volatile var code: Int, array: IntArray) {

    private val arrayUpdater = AtomicIntegerArray(array)

    fun incrementAndGet() {
        codeUpdater.incrementAndGet(this)
        println("AtomicIntegerFieldUpdater updater now is :${this.code}")
    }

    fun incrementAndGetArray(i: Int) {
    }

    companion object {
        private val codeUpdater = AtomicIntegerFieldUpdater.newUpdater(M::class.java, "code")
    }
}

fun atomicReference() {
    val m = M(1, IntArray(10) {
        it + 1
    })
    val m1 = M(2, IntArray(10) {
        it + 2
    })
    val currentVersion = 1
    val newStamp = currentVersion + 1
    // 解决ABA问题
    val asr = AtomicStampedReference<M>(m, currentVersion)

    println(asr.compareAndSet(m, m1, currentVersion, newStamp))
    println(m.code)
    println(asr.reference.code)

    m1.incrementAndGet()
}

// main
fun main() {
    val int = MyAtomicInt(123)
    println(int.getAndIncrement())
    println(int.get())

    atomicReference()
}