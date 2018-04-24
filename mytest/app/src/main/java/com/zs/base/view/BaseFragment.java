package com.zs.base.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-24 下午7:31
 * @description: mytest
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(createViewId(), null, true);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    protected abstract int createViewId();

    protected void initView(View view) {

    }

    protected void initData(Bundle bundle)
    {

    }


}
