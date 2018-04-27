package com.zs.test.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zs.mode.Info;

/**
 * Author:      ZangSong
 * Email:       gnoszsong@gmail.com
 * Date:        17-11-9 下午3:26
 * Description: mytest
 */
public class RemoteService extends Service {

    private Info remoteInfo;
    private OnCallInfo callBack;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IMyAidlInterface.Stub() {


            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public void printSomething(String content) throws RemoteException {
                Log.e("RemoteService", content);
            }

            @Override
            public String getFromClient() throws RemoteException {
                return "dsfd";
            }

            @Override
            public void addCallback(OnCallInfo callback) throws RemoteException {
                RemoteService.this.callBack = callback;
                Info s = new Info();
                s.setName("hah");
                callback.onCall(s);
            }

//            @Override
//            public void addCallback(com.zs.mode.Info callback) throws RemoteException {
//                remoteInfo = callback;
//                if (remoteInfo.getName() == null) {
//                    Log.e("addCallback", "null");
//                }
//                remoteInfo.setName("Server");
//            }
        };
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("RemoteService onCreate", "RemoteService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
