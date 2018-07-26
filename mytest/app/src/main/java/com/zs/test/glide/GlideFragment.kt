package com.zs.test.glide

import android.os.Bundle
import android.view.View
import butterknife.BindView
import com.zs.R
import com.zs.base.view.BaseFragment

/**
 * @author:      ZangSong
 * @email:       gnoszsong@gmail.com
 * @date:        18-7-26 下午7:54
 * @description: mytest
 */
class GlideFragment : BaseFragment() {

    @BindView(R.id.glide)
    var glideImageView = null

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)
    }

    override fun initView(view: View?) {
        super.initView(view)
        GlideApp.with(this).load("http://goo.gl/gEgYUd").into(imageView);
    }

    override fun createViewId(): Int {
        return R.layout.main
    }


}