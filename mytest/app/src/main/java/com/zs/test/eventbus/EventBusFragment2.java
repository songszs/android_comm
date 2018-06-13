package com.zs.test.eventbus;

import android.util.Log;
import android.view.View;

import com.zs.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-7 下午5:26
 * @description: mytest
 */
public class EventBusFragment2 extends BaseEventBusFragment {
    @Override
    protected int createViewId() {
        return R.layout.main;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        EventBus.builder().addIndex(new EventBusTest()).installDefaultEventBus();
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new CustomEvent("我测试一下EventBus"));
    }

    @OnClick(R.id.hello)
    public void onClickHello(View v)
    {
        EventBus.getDefault().post(new CustomEvent("我测试一下EventBus"));
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentEvent2(CustomEvent customEvent)
    {
        Log.e("EventBusFragment2",customEvent.getMessage());
    }

}
