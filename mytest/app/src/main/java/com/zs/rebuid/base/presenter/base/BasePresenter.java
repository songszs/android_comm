package com.zs.rebuid.base.presenter.base;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 17:39
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class BasePresenter implements IPresenter {

    protected Map<String,BasePresenter> presenters = new HashMap<>();

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    public BasePresenter getPresenter(String key)
    {
        return presenters.get(key);
    }
}
