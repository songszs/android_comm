package com.zs.test.kotlin

import android.util.Log

/**
 * @author:      ZangSong
 * @email:       gnoszsong@gmail.com
 * @date:        18-7-11 下午3:17
 * @description: mytest
 */
class StaticMehtod {
    //伴生对象
    companion object {
        //静态方法
        fun calc(a: Int, b: Int): Int = a + b
    }

    //普通方法
    fun commonFun() = Log.d("StaticMehtod", "commonFun")
}