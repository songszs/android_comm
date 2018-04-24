package com.zs.mytest;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zs.test.aidl.AIDLFragment;
import com.zs.base.view.BaseFragment;

public class MainActivity extends AppCompatActivity {

    BaseFragment mBaseFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }

    protected void init() {
        mBaseFragment = new AIDLFragment();
        FragmentManager     fragmentManager     = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.constraintLayout, mBaseFragment);
        fragmentTransaction.commit();
    }
}
