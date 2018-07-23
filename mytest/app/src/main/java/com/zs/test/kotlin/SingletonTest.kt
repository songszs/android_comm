package com.zs.test.kotlin

import android.util.Log

/**
 * @author:      ZangSong
 * @email:       gnoszsong@gmail.com
 * @date:        18-7-11 下午3:15
 * @description: mytest
 */
//单例模式使用object关键字
object SingletonTest {

    fun getTestValue(): String = "哈哈"

    fun setValue(value: String) {
        Log.e("哈哈哈", value)
    }
}