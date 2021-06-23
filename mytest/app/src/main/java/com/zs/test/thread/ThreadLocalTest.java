package com.zs.test.thread;

import android.util.Log;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-12 下午8:07
 * @description: mytest
 */
public class ThreadLocalTest {

    private static  final String TAG = "WaitNofityTest";

    ThreadLocal<String> num = new ThreadLocal<String>()
    {
        @Override
        protected String initialValue() {
            return "1";
        }
    };

    public void testThreadLocal()
    {
        new Thread1().start();
        new Thread2().start();
        new Thread3().start();
    }


    final class Thread1 extends Thread
    {
        @Override
        public void run() {
            synchronized (Thread1.class)
            {
                Log.e(TAG, "ThreadLocalTest Thread1 "+num.get());
                num.set("2");
                Log.e(TAG, "ThreadLocalTest Thread1 "+num.get());
            }
        }
    }


    final class Thread2 extends Thread
    {
        @Override
        public void run() {
            synchronized (Thread2.class) {
                Log.e(TAG, "ThreadLocalTest Thread2 " + num.get());
            }
        }
    }

    final class Thread3 extends Thread
    {
        ThreadLocal<String> num = new ThreadLocal<String>();
        @Override
        public void run() {
            super.run();
            Log.e(TAG, "ThreadLocalTest Thread3 "+num.get());
        }
    }
}
