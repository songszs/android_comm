package com.zs.test.jetpack.coroutines

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.zs.R
import com.zs.base.view.BaseFragment
import kotlinx.coroutines.*

/**
 * @author:       zang song
 * @version:      V1.0
 * @date:         2021/7/7 8:29 上午
 * @email:        gnoszsong@gmail.com
 * @description:  description
 */
class CoroutinesFragment : BaseFragment() {
    override fun createViewId(): Int {
        return R.layout.main
    }

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)
//        testRunBlock()
//        testAwait()
        testAwait2()
    }


    fun testGlobalScope() {
        // 在携程维护的线程池执行
        GlobalScope.launch(Dispatchers.IO) {
            //延时一秒
            delay(1000)
            log("launch")
        }
        //主动休眠两秒
        Thread.sleep(500)
        log("end")
    }

    fun testRunBlock() {
        // case1
//        log("main thread will block")
//        runBlocking(Dispatchers.IO) {
//            doWorkA()
//            delay(1000)
//            doWorkB()
//        }
//        // 阻塞线程，等上面协程执行完后继续执行
//        runBlocking(Dispatchers.IO) {
//            log("doWorkC")
//            delay(500)
//            log("doWorkD")
//        }
//        log("main thread block end")

        // case2
//        // ----全局协程没有阻塞------
//        GlobalScope.launch {
//            doWorkA()
//            delay(1000)
//            doWorkB()
//        }
//        GlobalScope.launch {
//            log("doWorkC")
//            delay(500)
//            log("doWorkD")
//        }

        // case3
        runBlocking() {
            log("doWorkA")
            // 线程转去做其他事情 500ms后回来执行
            delay(500)
            log("doWorkB")
            GlobalScope.launch {
                // 执行全局协程
                // 后于G，因为启动协程需要时间
                log("doWorkC")
                delay(200)
                log("doWorkD")
            }
            GlobalScope.launch {
                // 后于G，因为启动协程需要时间
                log("doWorkM")
                delay(100)
                log("doWorkN")
            }
            log("doWorkG")
            delay(300)
            log("doWorkH")
        }
        // 阻塞
        GlobalScope.launch {
            log("doWorkE")
            delay(100)
            log("doWorkF")
        }

        // case4
//        runBlocking {
//            launch {
//                repeat(3) {
//                    delay(100)
//                    log("launchA - $it")
//                }
//            }
//            launch {
//                repeat(3) {
//                    log("launchB - $it")
//                }
//            }
        // 内部不会阻塞
//            GlobalScope.launch {
//                repeat(3) {
//                    delay(120)
//                    log("GlobalScope - $it")
//                }
//            }
//        }
    }

    private fun testAwait() {
        lifecycleScope.launch(Dispatchers.Main) {
            log("start main job1")
            val getDataJob = lifecycleScope.async(Dispatchers.IO) {
                log("start io job1")
                getDataFromNetwork()
            }
            // 此时释放主线程,等待io线程执行完返回结果后回来继续执行
            // await也是挂起函数
            // 因为主线程被释放，仍可执行job2
            val data = getDataJob.await()
            log(data)
            log("end main job1")
        }


        lifecycleScope.launch(Dispatchers.Main) {
            log("start main job2")
            delay(500)
            log("end main job2")
        }
    }

    private fun testAwait2() {
        lifecycleScope.launch(Dispatchers.Main) {
            // 使用挂起函数，来切换协程执行
            // suspend 挂起当前函数，去io线程执行，执行完之后在回复执行下面
            val result = getDataFormDisk()
            log("had get result $result")
        }
    }
    private suspend fun getDataFormDisk():String = withContext<String>(Dispatchers.IO) {
        log("start getDataFormDisk")
        Thread.sleep(1000)
        log("end getDataFormDisk")

        "data from disk result"
    }

    private fun getDataFromNetwork(): String {
        Thread.sleep(1000)
        return "network result"
    }

    private suspend fun doWorkA() {
        log("doWorkA")
    }

    suspend fun doWorkB() {
        log("doWorkB")
    }

    private fun log(msg: Any?) = println("[${Thread.currentThread().name}] $msg")
}