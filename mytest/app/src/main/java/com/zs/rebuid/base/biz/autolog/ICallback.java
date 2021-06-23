package com.zs.rebuid.base.biz.autolog;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 20:27
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public interface ICallback<T> {
    void onResult(T result);
    void onError();
    void onTimeout();
}
