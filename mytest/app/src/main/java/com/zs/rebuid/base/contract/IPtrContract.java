package com.zs.rebuid.base.contract;

import com.zs.rebuid.base.view.base.IBaseView;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 19:52
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public interface IPtrContract {

    public interface IView extends IBaseView<IPresenter> {

        void finishPull();
        void finishRresh();

    }

    public interface IPresenter {


        void refresh();
        void loadMore();
    }
}
