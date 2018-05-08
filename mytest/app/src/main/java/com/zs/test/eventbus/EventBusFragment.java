package com.zs.test.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zs.R;
import com.zs.base.router.AuthRouterManager;
import com.zs.base.view.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-4 下午4:31
 * @description: mytest
 */
public class EventBusFragment extends BaseFragment {

    @BindView(R.id.hello)
    TextView mTextView;

    @Override
    protected int createViewId() {
        return R.layout.main;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CustomEvent customEvent)
    {
        mTextView.setText(customEvent.getMessage());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.hello)
    public void onClickHello(View v)
    {
        AuthRouterManager.getInstance().open(getActivity(),AuthRouterManager.URL_LOGIN_EVENTBUS2);
    }
}
