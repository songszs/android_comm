package com.zs.base;

import android.app.Application;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-25 下午5:11
 * @description: mytest
 */
public class MyTestApplication extends Application {

    public static PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }


    private void init() {
        // 初始化patch管理类
        mPatchManager = new PatchManager(this);

        String appVersion = null;
        try {
            appVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mPatchManager.init(appVersion);
        // 加载已经添加到PatchManager中的patch
        mPatchManager.loadPatch();
    }
}
