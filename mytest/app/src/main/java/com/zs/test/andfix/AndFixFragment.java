package com.zs.test.andfix;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.zs.R;
import com.zs.base.MyTestApplication;
import com.zs.base.view.BaseFragment;

import java.io.IOException;

import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-25 下午4:15
 * @description: mytest
 */
public class AndFixFragment extends BaseFragment {


    private String patchFileStr;
    private static final String APATCH_PATH = "/test.apatch";

    @Override
    protected int createViewId() {
        return R.layout.main;
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        patchFileStr = Environment.getExternalStorageDirectory().getAbsolutePath() + APATCH_PATH;
    }

    @OnClick(R.id.hello)
    public void clickTextView(View view) {
        try {
            MyTestApplication.mPatchManager.addPatch(patchFileStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getContext(), "测试之前", Toast.LENGTH_LONG).show();
//        Toast.makeText(getContext(), "测试之后", Toast.LENGTH_LONG).show();
    }
}
