package com.zs.rebuid.base.view;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.zs.R;
import com.zs.rebuid.base.contract.IToolbarContract;
import com.zs.rebuid.base.view.base.BaseContentViewWrapper;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 17:45
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class ToolbarWrapper extends BaseContentViewWrapper implements IToolbarContract.IView {


    View contentView;

    IToolbarContract.IPresenter presenter;

    Toolbar toolbar;

    protected TextView toolbarTitle;

    protected TextView toolbarMenu;

    protected ImageView toolbarBack;

    protected ViewGroup toolbarContainer;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void init(Context context) {

        toolbar = (Toolbar) LayoutInflater.from(context).inflate(R.layout.view_toolbar,null);

        toolbarContainer = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_toolbar_wrapper,null);

        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarMenu = (TextView) toolbar.findViewById(R.id.toolbar_menu);
        toolbarBack = (ImageView) toolbar.findViewById(R.id.toolbar_back);

        toolbarContainer.addView(toolbar);

        if(toolbarBack != null ){
            toolbarBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.backPress();
                }
            });
        }
    }

    @Override
    public void setContentView(View contentView) {
        if(contentView!= null){
            toolbarContainer.addView(contentView);
        }

    }

    @Override
    public View getContentView() {
        return contentView;
    }

    @Override
    public View getWrapperView() {
        return toolbar;
    }

    @Override
    public View getView() {
        return toolbarContainer;
    }

    @Override
    public void setTitle(String text) {
        toolbarTitle.setText(text);
    }

    @Override
    public void setMenu(String text) {
        toolbarMenu.setText(text);
    }


    @Override
    public void setPresenter(IToolbarContract.IPresenter iPresenter) {
        presenter = iPresenter;
    }
}
