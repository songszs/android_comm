package com.zs.rebuid.base.view;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zs.R;
import com.zs.rebuid.base.contract.IPtrContract;
import com.zs.rebuid.base.view.base.BaseContentViewWrapper;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 19:55
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class PtrWrapper extends BaseContentViewWrapper implements IPtrContract.IView, PtrHandler {

    ViewGroup prtContainer;
    View contenView;
    IPtrContract.IPresenter presenter;

    protected PtrFrameLayout ptrFrameLayout;
    protected LinearLayout content;

    @Override
    public void finishPull() {
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void finishRresh() {
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void init(Context context) {

        prtContainer = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.fragment_ptr,null);

//        prtContainer.removeView(prtContainer.findViewById(R.id.ptr_container_placeholder));
        content= prtContainer.findViewById(R.id.ptr_layout_content);

        ptrFrameLayout = (PtrFrameLayout) prtContainer.findViewById(R.id.ptr_layout);
        ptrFrameLayout.setPtrHandler(this);
        ptrFrameLayout.setMode(PtrFrameLayout.Mode.REFRESH);


    }

    @Override
    public void setPresenter(IPtrContract.IPresenter iPresenter) {

        this.presenter = iPresenter;
    }

    @Override
    public void setContentView(View contentView) {
        if(contentView!= null){
            contenView = contentView;
//            prtContainer.addView(contenView);
            content.addView(contenView);
        }
    }

    @Override
    public View getContentView() {
        return contenView;
    }

    @Override
    public View getWrapperView() {
        return prtContainer;
    }

    @Override
    public View getView() {
        return prtContainer;
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return true;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if(presenter != null){
            presenter.refresh();
        }
    }
}
