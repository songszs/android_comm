package com.zs.rebuid.base.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zs.R;
import com.zs.rebuid.base.contract.IListContract;
import com.zs.rebuid.base.contract.IToolbarContract;
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
    public void setAdapter(RecyclerView.Adapter adapter) {

    }

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
}
