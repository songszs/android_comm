//package com.zs.rebuid.base.viewmodel;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//
//import java.nio.ByteBuffer;
//
///**
// * @author: zang song
// * @version: V1.0
// * @date: 2020-03-17 21:33
// * @email: gnoszsong@gmail.com
// * @description: description
// */
//public class TcpReceiver {
//
//    LruByteBufferPool poll;
//
//
//    Handler mainHandler = new Handler(Looper.getMainLooper()) {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//        }
//    };
//
//    //netty接受数据
//    //1。解析包头
//    //2。读取长度。获取一个对象。
//    //3。从nettybuf中解密数据到messge中
//    //4。发送到主线程
//    public void onReceice(ByteBuffer byteBuffer) {
//
//        //1。 校验包是否合法 根据头读出长度
//        int length = 0;
//        //2。 然后obtin一个长度的Message。
//        //3。 从byteBuffer读出数据并解密到Message中。
//        LruByteBufferPool.getInstance().get(length);
//        //4。 发送到主线程。
//    }
//
//
//    //主线程
//    // 5。根据协议号解析成对应的bean
//    // 6。根据id找到对应请求，并从队列中移除
//    // 7。执行回调
//    public void doCallback(){
//        //1. 根据协议号解析出ProtoMsg
//        //2. 根据ProtoMsg解析出对应的Bean
//        //3. 回收Message
//        //3. 从请求队列头开始查找对应ID的请求对象
//        //4. 获取其Callback执行回调
//    }
//}
