package com.zs.test.andfix;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.zs.R;
import com.zs.base.MyTestApplication;
import com.zs.base.view.BaseFragment;

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

    private static final String APATCH_TEST_1 = "测试之前1";
    private              String APATCH_TEST_2 = "测试之前2";

//        private static final String APATCH_TEST_1 = "测试之前3";
//        private              String APATCH_TEST_2 = "测试之前4";

    private int index = 0;

    @Override
    protected int createViewId() {
        return R.layout.main;
    }


    private static final int      REQUEST_EXTERNAL_STORAGE = 1;
    private static       String[] PERMISSIONS_STORAGE      = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                                                                "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        patchFileStr = Environment.getExternalStorageDirectory().getAbsolutePath() + APATCH_PATH;
        verifyStoragePermissions(getActivity());

        try {
            MyTestApplication.mPatchManager.addPatch(patchFileStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.hello)
    public void clickTextView(View view) {
        Log.e(APATCH_TEST_1, "哈哈哈");
        Log.e(APATCH_TEST_2, "哈哈哈");

        index++;
        Log.e(APATCH_TEST_1, String.valueOf(index));

                if (index % 2 == 0) {
                    clickText2(null);
                }else
                {
                    clickText3(null);
                }
    }

        public void clickText2(View view) {
            Log.e("测试新增方法","哈哈哈");
        }

        public void clickText3(View view) {
            Log.e("测试新增类","哈哈哈");
            new AddTestClass().addTest("测试新增类");
        }
}
