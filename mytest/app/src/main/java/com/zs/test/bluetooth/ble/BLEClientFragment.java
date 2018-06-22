package com.zs.test.bluetooth.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import static com.zs.test.bluetooth.ble.BLEServerFragment.SERVER_UUID;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-21 下午5:53
 * @description: mytest
 */
public class BLEClientFragment extends BaseFragment {

    BluetoothAdapter      mBluetoothAdapter;
    BluetoothLeAdvertiser mBluetoothLeAdvertiser;

    AdvertiseCallback mAdvertiseCallback;

    final static String TEST = "h";
    private static final String TAG = "BLEClientFragment";

    @Override
    protected int createViewId() {
        return R.layout.main;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mAdvertiseCallback = new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                super.onStartSuccess(settingsInEffect);
                Log.e(TAG, "onStartSuccess");
            }

            @Override
            public void onStartFailure(int errorCode) {
                super.onStartFailure(errorCode);
                Log.e(TAG, "onStartFailure "+ errorCode);
            }
        };
        sendBrocast();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void sendBrocast() {
        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();

        AdvertiseSettings     settings    = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER)
                .setTimeout(0)
                .build();
        AdvertiseData data = new AdvertiseData.Builder()
                .addServiceUuid(SERVER_UUID)
                .setIncludeDeviceName(true)
                .build();

        //扫描响应数据(可选，当客户端扫描时才发送)
//        AdvertiseData scanResponse = new AdvertiseData.Builder()
                //设备厂商数据，自定义
//                .addManufacturerData(2, new byte[]{66, 66})
                //服务UUID
//                .addServiceUuid(new ParcelUuid(SERVER_UUID_))
                //      .addServiceData(new ParcelUuid(UUID_SERVICE), new byte[]{2}) //服务数据，自定义
//                .build();

        mBluetoothLeAdvertiser.startAdvertising(settings,data,mAdvertiseCallback);
    }
}
