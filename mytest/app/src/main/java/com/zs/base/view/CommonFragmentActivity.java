package com.zs.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.zs.base.config.BundleKeys;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-24 下午8:29
 * @description: mytest
 */
public class CommonFragmentActivity extends BaseActivity {

    protected Fragment mFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Intent intent    = getIntent();
        String className = intent.getStringExtra(BundleKeys.KEY_FRAGMENT_NAME);
        try {
            mFragment = (Fragment) Class.forName(className).newInstance();
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, mFragment).commitAllowingStateLoss();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
