package com.zs.test.annotation.generate.method;

import android.util.Log;

import com.zs.annotation.TestClass;
import com.zs.annotation.TestField;
import com.zs.annotation.TestMethod;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-15 上午10:50
 * @description: mytest
 */
@TestClass
public class Switch extends BaseDevice {

    @TestField
    private String switchFiled;

    @TestMethod
    public void off()
    {
        Log.e("Switch","off");
    }
}
