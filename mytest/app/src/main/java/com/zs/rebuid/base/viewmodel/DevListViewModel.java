package com.zs.rebuid.base.viewmodel;

import com.zs.rebuid.base.biz.autolog.ICallback;
import com.zs.rebuid.base.biz.autolog.IDevListContract;
import com.zs.rebuid.base.biz.bean.DevBean;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 20:33
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class DevListViewModel implements IDevListContract.IModel {
    @Override
    public void getAutoLog(int id, ICallback<DevBean> callback) {

    }
}
