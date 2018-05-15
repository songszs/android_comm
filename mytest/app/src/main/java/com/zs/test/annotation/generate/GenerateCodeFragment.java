package com.zs.test.annotation.generate;

import android.os.Bundle;

import com.zs.R;
import com.zs.annotation.GenerateTest;
import com.zs.base.view.BaseFragment;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-10 下午4:02
 * @description: mytest
 */
@GenerateTest
public class GenerateCodeFragment extends BaseFragment {
    @Override
    protected int createViewId() {
        return R.layout.main;
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
//        Toast.makeText(getContext(), new GeneratedClass().getMessage(), Toast.LENGTH_LONG).show();
    }
}
