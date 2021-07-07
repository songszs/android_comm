package com.zs.test.jetpack.viewmodel

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.*


/**
 * @author:       zang song
 * @version:      V1.0
 * @date:         2021/6/29 8:46 上午
 * @email:        gnoszsong@gmail.com
 * @description:  description
 */
class MyPresenter(fragment: Fragment) : LifecycleObserver {

    private val userProfileViewModel by lazy {
        fragment?.let {
            ViewModelProvider(it).get(UserProfileViewModel::class.java)
        }
    }

    val userLiveData by lazy {
        userProfileViewModel.userLiveData
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner) {
        Log.d("MyPresenter", "onCreate ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        Log.d("MyPresenter", "onDestroy ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onLifecycleChanged(
        owner: LifecycleOwner,
        event: Lifecycle.Event
    ) {
        Log.d("MyPresenter", "onLifecycleChanged $event")
    }
}