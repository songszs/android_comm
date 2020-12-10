package com.zs.test;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zs.R;
import com.zs.base.router.AuthRouterManager;
import com.zs.test.jni.HiJni;

public class MainActivity extends AppCompatActivity {

    Handler mainLooper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //        init();
//        mainLooper = new Handler(getMainLooper());
//        mainLooper.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AuthRouterManager.getInstance().open(MainActivity.this, AuthRouterManager.URL_LOGIN_RECYCLE_VIEW);
//            }
//        }, 1000);
        HiJni hiJni = new HiJni();
        String hi = hiJni.sayHello();
        Log.e("MainActivity ", "MainActivity: " + hi);
        hiJni.setNum("6");

    }

    protected void init() {
        findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthRouterManager.getInstance().open(MainActivity.this, AuthRouterManager.URL_LOGIN_RETROFIT);
            }
        });
    }
}
