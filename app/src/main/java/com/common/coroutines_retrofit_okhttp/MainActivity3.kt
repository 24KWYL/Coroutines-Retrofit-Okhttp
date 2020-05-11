package com.common.coroutines_retrofit_okhttp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity3 : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity3"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coroutineConetxt()
    }

    /**
     * 协程上下文（实际控制协程在那个线程执行）
     * launch和async都可接收CoroutineContext函数控制协程执行的线程
     * Dispatchers.Unconfined一种特殊的调度器（非受限调度器），运行在默认的调度者线程，挂起后恢复其他的协程执行。
     * Dispatchers.Default 默认调度器，采用后台共享的线程池（不传上下文，默认采用这种）
     * newSingleThreadContext 单独生成一个线程
     * Dispatchers.Main 主线程
     * Dispatchers.IO IO线程
     */
    fun coroutineConetxt() = runBlocking {
        launch { // 运行在父协程的上下文中，即 runBlocking 主协程
            Log.d(TAG, "Im working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) { // 不受限的——将工作在主线程中
            Log.d(TAG, "Unconfined before I'm working in thread ${Thread.currentThread().name}")
            delay(500)
            Log.d(TAG, "Unconfined after I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) { // 将会获取默认调度器
            Log.d(TAG, "I'm working in thread ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("MyOwnThread")) { // 将使它获得一个新的线程
            Log.d(TAG, "I'm working in thread ${Thread.currentThread().name}")
        }

        launch (Dispatchers.Main){
            Log.d(TAG, "I'm working in thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.IO) {
            Log.d(TAG, "I'm working in thread ${Thread.currentThread().name}")
        }
    }

    /**
     * withContext 线程切换
     */
    fun switchThread()= runBlocking{
        launch {
            Log.d(TAG, "start in thread ${Thread.currentThread().name}")
            val job= withContext(newSingleThreadContext("switchThread")){
                Log.d(TAG, "I'm working in thread ${Thread.currentThread().name}")
            }
            Log.d(TAG, "end in thread ${Thread.currentThread().name}")
        }

    }

    /**
     * 协程作用域
     */

}