package com.zs.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-24 下午8:29
 * @description: mytest
 */
public class CommonFragmentActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Intent intent = getIntent();
    }
}
