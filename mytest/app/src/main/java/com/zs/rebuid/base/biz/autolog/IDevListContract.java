package com.zs.rebuid.base.biz.autolog;

import com.zs.rebuid.base.biz.bean.DevBean;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 20:26
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public interface IDevListContract {

    public interface IModel{
        void getAutoLog(int id, ICallback<DevBean> callback);
    }
}
