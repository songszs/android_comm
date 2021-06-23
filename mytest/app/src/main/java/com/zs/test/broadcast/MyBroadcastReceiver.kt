package com.zs.test.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * @author:       zang song
 * @version:      V1.0
 * @date:         2019-12-20 18:45
 * @email:        gnoszsong@gmail.com
 * @description:  description
 */
class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "hello world", Toast.LENGTH_SHORT).show()
    }

}