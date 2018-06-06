package com.zs.test;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zs.R;
import com.zs.base.router.AuthRouterManager;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
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
