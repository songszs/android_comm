package com.zs.test.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-2 下午4:00
 * @description: mytest
 */
public interface GetBookDetail {

    @GET("book/{id}")
    Call<BookInfo> getBookInfo(@Path("id")String bookId);
}
