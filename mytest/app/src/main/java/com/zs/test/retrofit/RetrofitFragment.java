package com.zs.test.retrofit;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-2 下午3:59
 * @description: mytest
 */
public class RetrofitFragment extends BaseFragment {

    @BindView(R.id.hello)
    TextView hello;

    @Override
    protected int createViewId() {
        return R.layout.main;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        hello.setText("哈哈哈");
    }

    @OnClick(R.id.hello)
    public void onClickHello() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.douban.com/v2/")
                                                  .addConverterFactory(GsonConverterFactory.create()).
                                                          build();
        GetBookDetail  getBookDetail = retrofit.create(GetBookDetail.class);
        Call<BookInfo> bookInfoCall  = getBookDetail.getBookInfo("1220562");
        bookInfoCall.enqueue(new Callback<BookInfo>() {
            @Override
            public void onResponse(Call<BookInfo> call, Response<BookInfo> response) {
                BookInfo book = response.body();
                Log.e("book:", book.getSummary());
                hello.setText(book.getSummary());
            }

            @Override
            public void onFailure(Call<BookInfo> call, Throwable t) {
                Log.e("book:", "onFailure");
                t.printStackTrace();
            }
        });

    }
}
