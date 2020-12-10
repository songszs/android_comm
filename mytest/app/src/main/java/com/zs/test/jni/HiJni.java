package com.zs.test.jni;

import android.util.Log;

public class HiJni {

    public String name;

    static {
        System.loadLibrary("hi_jni");
    }

    /**
     * jni
     * @return
     */
    public native String sayHello();
    public native int setNum(String num);

    /**
     *
     */
    public void setName(String name){
        Log.e("HiJni","set name  " + name);
        this.name = name;
    }
}
