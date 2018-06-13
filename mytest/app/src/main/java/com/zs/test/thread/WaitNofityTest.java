package com.zs.test.thread;

import android.content.Context;
import android.os.Handler;
import android.util.Log;


/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-12 下午4:28
 * @description: mytest 交替输出多线程12
 */
public class WaitNofityTest {
    private static  final String TAG = "WaitNofityTest";
    private Context mContext;

    public Object lock;

    Thread thread1;
    Thread thread2;

    public WaitNofityTest(Context context)
    {
        mContext = context;
    }

    public void testWartNotify()
    {
        lock = new Object();


        thread1 =  new MyThead1(lock);
        thread2 =  new MyThead2(lock);

        thread1.start();
        thread2.start();

        new Handler(mContext.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //阻塞时调用中断会异常
                //非阻塞时可以用isInterrupted进行判断
                thread1.interrupt();
                thread2.interrupt();
            }
        }, 5000);
    }


    public class MyThead1 extends Thread
    {
        private Object lock;

        public MyThead1(Object lock)
        {
            this.lock = lock;
        }
        @Override
        public void run() {
            synchronized (lock)
            {
                try {
                    while (!this.isInterrupted())
                    {
                        //wait是在object的方法，需在同步代码块中调用，即表示阻塞正在持有该锁的线程
                        Log.e(TAG, getId()+": 1");
                        //先唤醒其他线程，不然会都处于阻塞的状态没有被唤醒
                        lock.notify();
                        //阻塞当前线程
                        lock.wait();
                    }
                    Log.e(TAG,getId()+"正常退出");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e(TAG,getId()+"中断异常退出");
                }
            }
        }
        }

    public class MyThead2 extends Thread
    {
        private Object lock;

        public MyThead2(Object lock)
        {
            this.lock = lock;
        }
        @Override
        public void run() {
                synchronized (lock)
                {
                    try {
                        while (!isInterrupted()) {
                            //wait是在object的方法，需在同步代码块中调用，即表示阻塞正在持有该锁的线程
                            Log.e(TAG,getId()+": 2");
                            //唤醒其他可以竞争此锁的线程
                            lock.notify();
                            //阻塞当前线程
                            lock.wait();
                        }
                        Log.e(TAG,getId()+"正常退出");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.e(TAG,getId()+"中断异常退出");
                    }
                }
        }
    }
}


