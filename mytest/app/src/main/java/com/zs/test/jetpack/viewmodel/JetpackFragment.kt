package com.zs.test.jetpack.viewmodel

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zs.R
import com.zs.base.view.BaseFragment

/**
 * @author:       zang song
 * @version:      V1.0
 * @date:         2021-06-23 08:19
 * @email:        gnoszsong@gmail.com
 * @description:  description
 */
class JetpackFragment : BaseFragment() {

    var myPresenter : MyPresenter? = null
    override fun createViewId(): Int {
        return R.layout.main
    }

    override fun initView(view: View?) {
        super.initView(view)
        view?.findViewById<Button>(R.id.hello)?.setOnClickListener {
            myPresenter?.userLiveData?.value.apply {
                this?.age = 30
                this?.name = "zangsong"
            }.let {
                myPresenter?.userLiveData?.postValue(it)
            }
        }
    }

    override fun initData(bundle: Bundle?) {
        super.initData(bundle)
        myPresenter = MyPresenter(this)
        lifecycle.addObserver(myPresenter!!)
        myPresenter?.userLiveData?.observe(
            this,
            Observer<UserProfileViewModel.User> {
                Log.d("JetpackFragment", "age " + it.age)
                Log.d("JetpackFragment", "name " + it.name)
            }
        )
    }

}

