package com.zs.test.kotlin

import com.zs.R
import com.zs.base.view.BaseFragment

/**
 * @author:      ZangSong
 * @email:       gnoszsong@gmail.com
 * @date:        18-7-11 下午3:13
 * @description: mytest
 */
class KotlinTestFragment : BaseFragment() {

    override fun createViewId(): Int {
        return R.layout.main
    }

    fun test()
    {
        //单例
        SingletonTest.getTestValue()
        SingletonTest.setValue("hello world")

        StaticMehtod.calc(1,1)
    }
}