package com.zs.test.retrofit.dynamic.proxy;

import org.junit.Test;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-5 上午10:16
 * @description: mytest
 */
public class MyTestTest {
    @Test
    public void test1() throws Exception {
        MyTest myTest = new MyTest();
//        myTest.test();
        myTest.testDynamicProxy();

    }

}