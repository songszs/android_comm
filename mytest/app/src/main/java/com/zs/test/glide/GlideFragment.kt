package com.zs.test.glide

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.zs.R
import com.zs.base.view.BaseFragment

/**
 * @author:      ZangSong
 * @email:       gnoszsong@gmail.com
 * @date:        18-7-26 下午7:54
 * @description: mytest
 */
class GlideFragment : BaseFragment() {

    var glideImageView: ImageView? = null

    override fun createViewId(): Int {
        return R.layout.glide
    }

    override fun initView(view: View) {
        super.initView(view)
        glideImageView = view.findViewById(R.id.glide)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        glideImageView?.let {
            this.activity?.let { it1 -> Glide.with(it1).load("http://b.hiphotos.baidu.com/image/pic/item/908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg").into(it) }
            Log.e(TAG,"onActivityCreated")
        }
        Log.e(TAG,"onActivityCreated2")

    }


}