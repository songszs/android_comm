package com.zs.test.bluetooth.fastble;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.clj.fastble.BleManager;
import com.zs.R;
import com.zs.base.view.BaseFragment;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2019-08-19 16:57
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class BleConfigNetFragment extends BaseFragment {

    private static final String TAG = "FastBleFragment";
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 456;


    @Override
    protected int createViewId() {
        return R.layout.main;
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);

        BleManager.getInstance().init(getActivity().getApplication());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, yay! Start the Bluetooth device scan.
                    BleManager.getInstance()
                            .enableLog(true)
                            .setReConnectCount(10, 5000)
                            .setConnectOverTime(60000)
                            .setSplitWriteNum(160)
                            .setOperateTimeout(30000);
                    BleConfigNetManager.getInstance().scan();
                } else {
                    // Alert the user that this application requires the location permission to perform the scan.
                }
            }
        }
    }
}
