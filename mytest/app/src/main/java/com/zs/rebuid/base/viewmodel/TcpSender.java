//package com.zs.rebuid.base.viewmodel;
//
//import android.os.Handler;
//import android.os.HandlerThread;
//
//import com.zs.rebuid.base.biz.autolog.ICallback;
//
//import java.nio.ByteBuffer;
//import java.util.LinkedList;
//
///**
// * @author: zang song
// * @version: V1.0
// * @date: 2020-03-17 20:36
// * @email: gnoszsong@gmail.com
// * @description: description
// */
//public class TcpSender {
//
//
//    LinkedList<TimeoutableRequest> requested;
//
//    long timeout = 5 ;
//
//
//    NettyConnect nettyConnect;
//    HandlerThread handlerThread;
//
//
//
//    Handler handler = new Handler(handlerThread.getLooper());
//
//
//
//
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            checkTimeout();
//        }
//    };
//
//    //1。 放入队列
//    //2。 根据proto size计算包体size。获取一个缓存对象
//    //3。 发送给netty，把原始数据加密拷到nettybuf
//    //4。 释放对象
//    void sendMessage(Request request){
//
//        long timeout = System.currentTimeMillis();
//        //放进队列 添加超时处理
//        handler.postDelayed(runnable,timeout);
//
//        //从缓冲池中拿出一个对象
//        TcpRequestAdapter.TcpMessage message = LruByteBufferPool.getInstance().get(request.size);
//        //设置数据添加包头 加密由netty处理
//        message.setData(request.lite);
//
//        //发送请求
//        nettyConnect.send(message);
//        //释放对象
//        LruByteBufferPool.getInstance().release(message);
//    }
//
//    public class Request{
//        MessageLite lite;
//        ByteBuffer byteBuffer;
//        int code;
//        ICallback<?> callback;
//        int size;
//    }
//
//    public class TimeoutableRequest{
//
//        int timeout;
//        int requestTime;
//        public TimeoutableRequest(Request request){
//
//
//        }
//
//    }
//
//    public synchronized void removeRequest(){
//
//    }
//
//    public synchronized void addRequest(){
//
//    }
//
//
//
//
//    public void checkTimeout(){
//
//        long timeout = System.currentTimeMillis();
//
//        if(requested.getFirst().timeout <  1){
//
//        }
//        handler.post(runnable);
//
//    }
//
//
//    public class NettyConnect{
//
//        public void send(TcpRequestAdapter.TcpMessage message){
//
//        }
//    }
//}
