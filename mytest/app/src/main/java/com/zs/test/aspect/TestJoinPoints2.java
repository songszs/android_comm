package com.zs.test.aspect;

import android.util.Log;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-28 下午3:15
 * @description: mytest
 */
public class TestJoinPoints2 {

    public void testCall(int x)
    {
        Log.e("testCall", String.valueOf(x));
    }
}
