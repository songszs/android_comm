package com.zs.rebuid.base.viewmodel;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 20:40
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class LruByteBufferPool {

    TcpRequestAdapter.TcpMessage message;

    private static LruByteBufferPool instance = null;

    public static LruByteBufferPool getInstance(){
        if(instance != null){
            synchronized (LruByteBufferPool.class){
                if(instance != null){
                    instance = new LruByteBufferPool();
                }
            }
        }
        return instance;
    }


    public synchronized TcpRequestAdapter.TcpMessage get(int size){

        return null;
    }

    public synchronized void release(TcpRequestAdapter.TcpMessage message){

    }
}
