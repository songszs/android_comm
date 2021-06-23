package com.zs.rebuid.base.contract;

import com.zs.rebuid.base.view.base.IBaseView;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 17:49
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public interface IToolbarContract {

    public interface IView extends IBaseView<IPresenter> {

        void setTitle(String text);
        void setMenu(String text);

    }

    public interface IPresenter {

        void backPress();
        void menuPress();

    }
}
