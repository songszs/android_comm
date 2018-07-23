package com.zs.test.nio

import android.os.Bundle
import com.zs.R
import com.zs.base.view.BaseFragment
import kotlin.concurrent.thread

/**
 * @author:      ZangSong
 * @email:       gnoszsong@gmail.com
 * @date:        18-7-23 上午10:29
 * @description: mytest
 */
class NioFragment : BaseFragment(){


    override fun createViewId(): Int {
        return R.layout.main
    }

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)
        init()
    }

    fun init()
    {
        thread {
            val nioServer = NioServer()
            nioServer.initServer(8888)
            nioServer.listen()
        }

        thread {
            val nioClient = NioClient()
            nioClient.initClient("localhost",8888)
            nioClient.listen()
        }

    }
}