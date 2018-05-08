package com.zs.test.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zs.R;
import com.zs.base.view.BaseFragment;
import com.zs.mode.Info;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-24 下午7:32
 * @description: mytest
 */
public class AIDLFragment extends BaseFragment {

    private Button   mStart;
    private Button   mSend;
    private EditText mEditText;


    private ServiceConnection mConnection;
    private IMyAidlInterface  mInterface;

    private Info mInfo;

    private OnCallInfo mCallInfo;



    @Override
    protected int createViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mStart = view.findViewById(R.id.button);
        mSend = view.findViewById(R.id.button2);
        mEditText = view.findViewById(R.id.editText);

        final Activity activity = getActivity();

        Log.e("mStart onCreate", "mStart onCreate");
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, RemoteService.class);
                activity.startService(intent);
                Log.e("mStart onCreate", "mStart onCreate");

                activity.bindService(intent, mConnection, BIND_AUTO_CREATE);
            }
        });

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    mInterface.printSomething(mEditText.getText().toString());
                    mInterface.getFromClient();

                    mCallInfo = new OnCallInfo();


                    mInterface.addCallback(mCallInfo);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mInterface = null;
            }
        };
    }

    @Override
    protected void initData(Bundle bundle) {

    }


}
