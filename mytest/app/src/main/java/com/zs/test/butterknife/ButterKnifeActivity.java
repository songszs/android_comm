package com.zs.test.butterknife;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zs.R;
import com.zs.base.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-2 下午2:59
 * @description: mytest
 */
public class ButterKnifeActivity extends BaseActivity {

    @BindView(R.id.hello)
    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    @OnClick(R.id.hello)
    public void onClick()
    {
        mTextView.setText("呵呵呵");
    }
}
