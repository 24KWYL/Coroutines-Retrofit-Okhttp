package com.common.coroutines_retrofit_okhttp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        createCoroutines()
//        nestCoroutine1()
//        nestCoroutine2()
//        nestCoroutine3()
        makeCoroutineScope()
    }


    /**
     * 创建协程几种方式
     */
    fun createCoroutines() {
        /**
         * 1.GlobalScope.launch
         * 默认开启新线程创建协程
         * 非阻塞
         */
        val coroutineJob = GlobalScope.launch {
            Log.d(TAG, "current Thread is ${Thread.currentThread()}")
        }
        /**
         * 2.runBlocking
         * 默认在调用线程创建协程
         * 阻塞
         */
        val coroutine2 = runBlocking {
            Log.d(TAG, "current Thread is ${Thread.currentThread()}")
        }
        /**
         * 3.async与await
         * 默认开启新线程创建协程
         */
        val coroutineDeferred = GlobalScope.async {
            Log.d(TAG, "current Thread is ${Thread.currentThread()}")
        }
    }

    /**
     * 协程嵌套 runBlocking主协程内部创建子协程
     * delay协程挂起函数，协程挂起一定的时间
     * join挂起外部协程，直到当前job执行结束（子协程执行结束）
     */
    fun nestCoroutine1() = runBlocking {
        val job = GlobalScope.launch {
            delay(1000)
            Log.d(TAG, "launch current Thread is ${Thread.currentThread()}")
        }
        Log.d(TAG, "runBlocking current Thread is ${Thread.currentThread()}")
        Log.d(TAG, "runBlocking startTime:${System.currentTimeMillis()}")
        job.join()
        Log.d(TAG, "runBlocking endTime:${System.currentTimeMillis()}")
    }

    /**
     * 协程嵌套 launch主协程内部创建子协程
     * delay协程挂起函数，协程挂起一定的时间
     * join挂起外部协程，直到当前job执行结束（子协程执行结束）
     */
    fun nestCoroutine2() = GlobalScope.launch {
        val job = GlobalScope.launch {
            delay(1000)
            Log.d(TAG, "launch2 current Thread is ${Thread.currentThread()}")
        }
        Log.d(TAG, "launch1 current Thread is ${Thread.currentThread()}")
        Log.d(TAG, "launch1 startTime:${System.currentTimeMillis()}")
        job.join()
        Log.d(TAG, "launch1 endTime:${System.currentTimeMillis()}")
    }

    /**
     * 协程嵌套 runBlocking主协程内部创建子协程(runBlocking作用域内创建)
     * delay协程挂起函数，协程挂起一定的时间
     * 主协程等待子协程执行完毕，无需显示join
     */
    fun nestCoroutine3() = runBlocking {
        val job = launch {
            delay(1000)
            Log.d(TAG, "launch current Thread is ${Thread.currentThread()}")
        }
        Log.d(TAG, "runBlocking current Thread is ${Thread.currentThread()}")
        Log.d(TAG, "runBlocking startTime:${System.currentTimeMillis()}")
        Log.d(TAG, "runBlocking endTime:${System.currentTimeMillis()}")
    }

    /**
     * 协程作用域 coroutineScope创建协程作用域
     * runBlocking会等待协程作用域内执行结束
     */
    fun makeCoroutineScope() = runBlocking {
        launch {
            delay(200L)
            Log.d(TAG, "launch current Thread is ${Thread.currentThread()}")
        }

        coroutineScope() {
            // 创建一个协程作用域
            launch {
                delay(500L)
                Log.d(TAG, "coroutineScope launch current Thread is ${Thread.currentThread()}")
            }

            delay(100L)
            Log.d(TAG, "coroutineScope current Thread is ${Thread.currentThread()}")
        }

        Log.d(TAG, "runBlocking current Thread is ${Thread.currentThread()}")
    }


}