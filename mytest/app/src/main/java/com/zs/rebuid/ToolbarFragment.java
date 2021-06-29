package com.zs.rebuid;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.zs.R;
import com.zs.base.view.BaseFragment;
import com.zs.rebuid.base.ToolbarPresenter;
import com.zs.rebuid.base.view.NetworkViewWrapper;
import com.zs.rebuid.base.view.PtrWrapper;
import com.zs.rebuid.base.view.ToolbarWrapper;

import butterknife.ButterKnife;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 18:38
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class ToolbarFragment extends BaseFragment {

    ToolbarWrapper toolbarWrapper;

    NetworkViewWrapper networkViewWrapper;

    ToolbarPresenter presenter;

    PtrWrapper ptrWrapper;

    @Override
    protected int createViewId() {
        return R.layout.test_with_btn;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(createViewId(), null, false);
        mUnbinder = ButterKnife.bind(this,rootView);

        toolbarWrapper = new ToolbarWrapper();
        toolbarWrapper.init(getContext());


        networkViewWrapper = new NetworkViewWrapper();
        networkViewWrapper.init(getContext());
        networkViewWrapper.setContentView(toolbarWrapper.getView());
//        networkViewWrapper.setPresenter((INetworkViewContract.IPresenter) presenter.getPresenter("toobar"));


        ptrWrapper = new PtrWrapper();
        ptrWrapper.init(getContext());
        ptrWrapper.setContentView(rootView);
        ptrWrapper.setPresenter(presenter);


        toolbarWrapper.setContentView(ptrWrapper.getView());
        toolbarWrapper.setPresenter(presenter);
        toolbarWrapper.setMenu("hahah");

        return toolbarWrapper.getView();
    }


}
