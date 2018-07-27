package com.zs.test.glide

import android.os.Bundle
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

    @BindView(R.id.glide)
    var glideImageView: ImageView? = null

    override fun createViewId(): Int {
        return R.layout.glide
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        glideImageView?.let { Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532669164626&di=58881655d363a9c2d75659a7deaeac96&imgtype=0&src=http%3A%2F%2Fi.dyt1.cc%2Fc0%2F2b%2Fd4%2F55%2Fb4%2Fbf%2Fc0%2Fb4%2Fda%2F03%2Feb%2F98%2Fd1%2Fcf%2Fad%2F70.jpg").into(it) }
    }


}