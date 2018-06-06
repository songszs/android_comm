package com.zs.test.butterknife;

import android.view.View;
import android.widget.TextView;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-2 下午2:43
 * @description: mytest
 */
public class ButterKnifeFragment extends BaseFragment {

    @BindView(R.id.hello)
    TextView hello;

    @Override
    protected int createViewId() {
        return R.layout.main;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        hello.setText("哈哈哈");
    }

    @OnClick(R.id.hello)
    public void onClickHello()
    {
        hello.setText("嘿嘿嘿");
    }
}
