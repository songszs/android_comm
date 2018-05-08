package com.zs.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.zs.base.config.BundleKeys;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-24 下午8:29
 * @description: mytest
 */
public class CommonFragmentActivity extends AppCompatActivity {

    protected Fragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent    = getIntent();
        String className = intent.getStringExtra(BundleKeys.KEY_FRAGMENT_NAME);
        try {
            mFragment = (Fragment) Class.forName(className).newInstance();
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, mFragment).commitAllowingStateLoss();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setTitle(mFragment.getClass().getSimpleName());
    }
}
