package com.zs.test.aspect;

import android.util.Log;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-27 下午2:19
 * @description: mytest
 */
public class TestJoinPoints {

    public void testCall(int x)
    {
        Log.e("testCall", String.valueOf(x));
    }

    public boolean testReturnValue()
    {
        return true;
    }

    public void testUnCatchException()
    {
        throw new NullPointerException("My Test NullPointerException");
    }

    public void testFlow()
    {
        testCall(2000);
//        testReturnValue();
    }
}
