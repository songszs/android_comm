package com.zs.test.eventbus;

import android.util.Log;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-10 下午8:03
 * @description: mytest
 */
public class BaseEventBusFragment extends BaseFragment{
    @Override
    protected int createViewId() {
        String[] strings = new String[5];
        return R.layout.main;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentEvent2(CustomEvent customEvent)
    {
        Log.e("BaseEventBusFragment", customEvent.getMessage());
    }

//    public void onFragmentEvent2(CustomEvent customEvent)
//    {
//        Log.e("BaseFragment", customEvent.getMessage());
//    }

    public void onFragmentEvent4(CustomEvent customEvent)
    {
        Log.e("BaseFragment", customEvent.getMessage());
    }

    public void onFragmentEvent5(CustomEvent customEvent)
    {
        Log.e("BaseFragment", customEvent.getMessage());
    }
}
