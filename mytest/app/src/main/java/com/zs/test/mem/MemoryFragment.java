package com.zs.test.mem;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-02-27 15:12
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class MemoryFragment extends BaseFragment {
    @Override
    protected int createViewId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        showMemory();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showMemory(){
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        //最大分配内存
        int memory = activityManager.getMemoryClass();
        System.out.println("memory: "+memory);
        //最大分配内存获取方法2
        float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0/ (1024 * 1024));
        //当前分配的总内存
        float totalMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0/ (1024 * 1024));
        //剩余内存
        float freeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0/ (1024 * 1024));
        System.out.println("最大可用内存: "+maxMemory);
        System.out.println("当前可用内存: "+totalMemory);
        System.out.println("当前空闲内存: "+freeMemory);
        System.out.println("当前已用内存: "+ (totalMemory-freeMemory));

    }
}
