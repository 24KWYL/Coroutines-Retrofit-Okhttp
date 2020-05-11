package com.common.coroutines_retrofit_okhttp

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

class MainActivity2 : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        asyncCoroutines()
//        Log.d(TAG, "asyncCoroutines method invoke finish")
    }

    /**
     * async启动协程
     * 返回Deferred
     * 非阻塞
     */
    private fun asyncCoroutines() = runBlocking {
        var startTime = System.currentTimeMillis()
        val time = measureTimeMillis {
            val deferred1 = async {
                delay(2000L)
                Log.d(TAG, "deferred1 get result , current thread is ${Thread.currentThread()}")
            }

            val deferred2 = async {
                delay(3000L)
                Log.d(TAG, "deferred2 get result , current thread is ${Thread.currentThread()}")
            }

            Log.d(TAG, "result is ${deferred1.await() + deferred2.await()}")
        }
        Log.d(TAG, "cost time is $time")
        Log.d(TAG, "cost time2 is ${System.currentTimeMillis() - startTime}")

    }

    /**
     * 惰性async
     * deferred调用start或者awit才会运行协程
     */
    private fun lazyAsyncCoroutines() = runBlocking {
        var startTime = System.currentTimeMillis()
        val time = measureTimeMillis {
            val deferred1 = async(start=CoroutineStart.LAZY) {
                delay(2000L)
                Log.d(TAG, "deferred1 get result , current thread is ${Thread.currentThread()}")
            }

            val deferred2 = async(start=CoroutineStart.LAZY) {
                delay(3000L)
                Log.d(TAG, "deferred2 get result , current thread is ${Thread.currentThread()}")
            }
            deferred1.start()
            deferred2.start()
            Log.d(TAG, "result is ${deferred1.await() + deferred2.await()}")
        }
        Log.d(TAG, "cost time is $time")
        Log.d(TAG, "cost time2 is ${System.currentTimeMillis() - startTime}")
    }

    /***
     * coroutineScope作用域，结构化并发
     * 作用域内函数报错，作用域内协程都被取消，
     * 子协程出现异常，父协程和父协程中的其他协程都会取消
     */
    private fun globalScopeAsync()= runBlocking{
        val time = measureTimeMillis {
            println("The answer is ${concurrentSum()}")
        }
        println("Completed in $time ms")
    }

    suspend fun concurrentSum(): Int = coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        one.await() + two.await()
    }

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // 假设我们在这里做了些有用的事
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // 假设我们在这里也做了些有用的事
        return 29
    }
}