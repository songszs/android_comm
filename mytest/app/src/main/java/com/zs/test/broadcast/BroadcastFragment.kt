package com.zs.test.broadcast

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.zs.R
import com.zs.base.view.BaseFragment

/**
 * @author:       zang song
 * @version:      V1.0
 * @date:         2019-12-20 18:43
 * @email:        gnoszsong@gmail.com
 * @description:  description
 */
class BroadcastFragment : BaseFragment() {

    var broadcastReceiver: MyBroadcastReceiver? = null

    override fun createViewId(): Int {
        return R.layout.test_with_btn
    }

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)
        val intentFilter = IntentFilter()
        broadcastReceiver = MyBroadcastReceiver()
        intentFilter.addAction("MyTest")
        activity?.registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun initView(view: View?) {
        super.initView(view)
        view?.findViewById<Button>(R.id.test_btn)?.setOnClickListener {
            var intent = Intent()
            intent.action = "MyTest"
            activity?.sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(broadcastReceiver)
    }

}