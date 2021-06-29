package com.zs.rebuid.base.view;

import android.content.Context;
import android.view.View;


import androidx.recyclerview.widget.RecyclerView;

import com.zs.R;
import com.zs.rebuid.base.contract.IListContract;
import com.zs.rebuid.base.view.base.BaseContentViewWrapper;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 17:45
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class ListWrapper extends BaseContentViewWrapper implements IListContract.IView {


    @Override
    public void init(Context context) {

    }

    @Override
    public void setPresenter(IListContract.IPresenter iPresenter) {

    }

    @Override
    public void setContentView(View contentView) {

    }

    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public View getWrapperView() {
        return null;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {

    }
}
