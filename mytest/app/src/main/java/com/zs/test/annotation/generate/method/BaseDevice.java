package com.zs.test.annotation.generate.method;

import android.util.Log;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-15 上午10:49
 * @description: mytest
 */
public class BaseDevice implements IAction.IOpen {

    @Override
    public void open(String state) {
        Log.e("BaseDevice", "open");
    }
}
