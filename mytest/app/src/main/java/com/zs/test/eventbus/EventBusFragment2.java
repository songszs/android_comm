package com.zs.test.eventbus;

import android.view.View;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-7 下午5:26
 * @description: mytest
 */
public class EventBusFragment2 extends BaseFragment {
    @Override
    protected int createViewId() {
        return R.layout.main;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @OnClick(R.id.hello)
    public void onClickHello(View v)
    {
        EventBus.getDefault().post(new CustomEvent("我测试一下EventBus"));
    }
}
