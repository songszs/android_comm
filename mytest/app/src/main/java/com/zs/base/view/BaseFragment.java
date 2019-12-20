package com.zs.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-24 下午7:31
 * @description: mytest
 */
public abstract class BaseFragment extends Fragment {


    protected static String TAG = "BaseFragment";
    protected View     rootView;
    protected Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(createViewId(), null, true);
        mUnbinder = ButterKnife.bind(this,rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getCanonicalName();
        initData(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    protected abstract int createViewId();

    protected void initView(View view) {

    }

    protected void initData(Bundle bundle) {
    }


}
