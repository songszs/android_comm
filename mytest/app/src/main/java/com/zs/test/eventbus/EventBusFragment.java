package com.zs.test.eventbus;

import android.widget.Toast;

import com.zs.base.view.BaseFragment;
import com.zs.mytest.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-4 下午4:31
 * @description: mytest
 */
public class EventBusFragment extends BaseFragment {
    @Override
    protected int createViewId() {
        return R.layout.activity_main;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CustomEvent customEvent)
    {
        Toast.makeText(getActivity(),customEvent.getMessage(),Toast.LENGTH_LONG).show();
    }
}
