package com.zs.test.systrace

import android.os.Build
import android.os.Bundle
import android.os.Trace
import android.support.annotation.RequiresApi
import android.util.Log
import com.zs.R
import com.zs.base.view.BaseFragment

/**
 * @author:       zang song
 * @version:      V1.0
 * @date:         2020-01-09 21:50
 * @email:        gnoszsong@gmail.com
 * @description:  description
 */
class SystemTraceFragment : BaseFragment() {
    override fun createViewId(): Int {
        return R.layout.main
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SystemTraceFragment", "sleep start")
        Trace.beginSection("myTrace")

        Thread.sleep(1000)

        Trace.endSection()
        Log.d("SystemTraceFragment", "sleep end")


    }


}