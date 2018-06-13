package com.zs.test.thread;

import android.os.Bundle;
import android.view.View;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-12 下午4:26
 * @description: mytest
 */
public class ThreadFragment extends BaseFragment {
    WaitNofityTest waitNofityTest;
    ThreadLocalTest threadLocalTest;
    @Override
    protected int createViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
//        waitNofityTest = new WaitNofityTest(getActivity());
//        waitNofityTest.testWartNotify();

        threadLocalTest = new ThreadLocalTest();
        threadLocalTest.testThreadLocal();
    }

    @OnClick(R.id.button)
    public void clickB(View view)
    {
        waitNofityTest.testWartNotify();
    }
}
