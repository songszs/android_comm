package com.zs.test.aspect;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zs.base.view.BaseFragment;
import com.zs.mytest.R;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-24 下午8:14
 * @description: mytest
 */
public class AspectFragment extends BaseFragment {
    @Override
    protected int createViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestJoinPoints points = new TestJoinPoints();

        points.testCall(5);

        points.testReturnValue();

//        points.testUnCatchException();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
