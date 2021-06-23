package com.zs.test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-02-22 16:21
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class ThreadPoolTest {

   //https://juejin.im/post/5d1882b1f265da1ba84aa676#heading-38
    public void testThreadPool(){

        //单个线程 顺序执行。提交任务，如果有线程创建，添加任务到LinkedBlockingQueue阻塞队列。没有则创建线程。始终只有一个线程在工作。
        //LinkedBlockingQueue
        ExecutorService service1 = Executors.newSingleThreadExecutor();

        //可以创建无限个线程。如果提交任务速度大于处理任务速度，会创建过多到线程导致资源耗尽。
        //没有核心线程，所以任务直接加到SynchronousQueue队列
        //提交任务，如果有空闲线程则使用空闲线程执行。没有则创建线程执行。线程执行完会等待60s，过后被终止。
        //SynchronousQueue
        ExecutorService service2 = Executors.newCachedThreadPool();

        //提交任务
        //如果线程数少于核心线程，创建核心线程执行任务
        //如果线程数等于核心线程，把任务添加到LinkedBlockingQueue阻塞队列
        //如果线程执行完任务，去阻塞队列取任务，继续执行
        ExecutorService service3 = Executors.newFixedThreadPool(5);

        //单个线程 顺序执行 DelayedWorkQueue
        ExecutorService service4 = Executors.newScheduledThreadPool(5);

    }


}
