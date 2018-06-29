package com.zs.test.okhttp;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.zs.R;
import com.zs.base.view.BaseFragment;
import com.zs.test.retrofit.BookInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-25 下午7:58
 * @description: mytest
 */
public class OkHttpFragment extends BaseFragment {

    OkHttpClient client;

    @Override
    protected int createViewId() {
        return R.layout.main;
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        Request request = new Request.Builder()
                .get()
                .url("https://api.douban.com/v2/book/1220562")
                .build();


        client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
                                           //                .cache(cache).proxy(proxy).authenticator(authenticator)
                                           .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("OkHttpFragment ", "onFailure");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson     gson     = new Gson();
                String content = response.body().string();
                Log.e("OkHttpFragment ", content);
                BookInfo bookInfo = gson.fromJson(content, BookInfo.class);
                Log.e("OkHttpFragment ", bookInfo.getSummary());
            }
        });

    }
}
