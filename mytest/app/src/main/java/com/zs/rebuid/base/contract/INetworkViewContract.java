package com.zs.rebuid.base.contract;

import com.zs.rebuid.base.view.base.IBaseView;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 17:49
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public interface INetworkViewContract {

    public interface IView extends IBaseView<IPresenter> {

        void showContentView();
        void showLoading();

    }

    public interface IPresenter {


    }
}
