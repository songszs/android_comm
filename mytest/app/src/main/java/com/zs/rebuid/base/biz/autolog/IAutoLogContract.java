package com.zs.rebuid.base.biz.autolog;

import com.zs.rebuid.base.biz.bean.DevBean;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 17:29
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public interface IAutoLogContract {

    interface IView{
        void showLog(int page);
    }

    interface IPresenter{
        void delLog();
        void showAutoDetail(int id);
        void loadNext();
        void refresh();
    }

    interface IModel{
        void getAutoLog(int id, ICallback<DevBean> callback);
    }
}
