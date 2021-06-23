package com.zs.rebuid.base.view.base;

import android.view.View;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 17:42
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public interface IViewWrapper {

    void setContentView(View contentView);

    View getContentView();

    View getWrapperView();

    View getView();
}
