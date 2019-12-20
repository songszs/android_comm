package com.zs.dagger;

import android.util.Log;

import java.util.logging.Logger;

import javax.inject.Inject;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2019-10-30 09:45
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class LoginPresenter {

    ICommonView iView;


    @Inject
    public LoginPresenter(ICommonView iView) {
        this.iView = iView;
    }

    public void login() {
        Log.e("testdagger", "test dagger");
    }
}
