package com.zs.rebuid.base.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zs.R;
import com.zs.rebuid.base.contract.INetworkViewContract;
import com.zs.rebuid.base.contract.IToolbarContract;
import com.zs.rebuid.base.view.base.BaseContentViewWrapper;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 17:45
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class NetworkViewWrapper extends BaseContentViewWrapper implements INetworkViewContract.IView {


    View contentView;
    INetworkViewContract.IPresenter presenter;

    Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    @Override
    public View getContentView() {
        return contentView;
    }

    @Override
    public View getWrapperView() {
        return null;
    }

    @Override
    public View getView() {
        return contentView;
    }

    @Override
    public void showContentView() {

    }

    @Override
    public void showLoading() {
        Toast.makeText(context,"showloading",Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(INetworkViewContract.IPresenter iPresenter) {
        this.presenter = iPresenter;
    }
}
