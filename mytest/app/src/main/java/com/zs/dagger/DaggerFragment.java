package com.zs.dagger;

import android.os.Bundle;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import javax.inject.Inject;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2019-10-30 09:43
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class DaggerFragment extends BaseFragment implements ICommonView{

    @Inject
    LoginPresenter presenter;

    @Override
    protected int createViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        //往该类中注射对象
        DaggerCommonComponent.builder().commonModule(new CommonModule(this)).build().inject(this);
        presenter.login();
    }
}
