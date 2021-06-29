package com.zs.test.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * @author:       zang song
 * @version:      V1.0
 * @date:         2021-06-23 08:22
 * @email:        gnoszsong@gmail.com
 * @description:  description
 */
class UserProfileViewModel : ViewModel() {

    val userLiveData: MutableLiveData<User> by lazy {
        MutableLiveData<User>().also {
            val user = User("zs", 23)
            it.value = user
        }
    }

    inner class User(var name: String, var age: Int)

    fun loadUser() {

    }
}