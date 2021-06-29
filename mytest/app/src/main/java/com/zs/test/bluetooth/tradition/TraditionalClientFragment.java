package com.zs.test.bluetooth.tradition;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-20 下午3:01
 * @description: mytest
 */
public class TraditionalClientFragment extends BaseFragment {

    private static final String TAG = "BlueToothFragment";

    private BluetoothAdapter mBluetoothAdapter;

    private Set<BluetoothDevice> mBluetoothDevices;

    private BluetoothDevice target;

    private BluetoothSocket clientSocket;

    @Override
    protected int createViewId() {
        return R.layout.main;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    private boolean checkSupportBLE(Context context) {
        // 使用下面的方法来确定设备是否支持BLE, 然后你可以选择禁用BLE的功能
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Log.e(TAG, "不支持ble");
            return false;
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void initData(Bundle bundle) {
        // 初始化蓝牙适配器.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if(mBluetoothAdapter != null){
            // 确认设备支持蓝牙并且已经启用. 如果没有,
            // 显示一个对话框要求用户授权启用蓝牙.
            if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

            mBluetoothDevices = new HashSet<>();
            mBluetoothAdapter.startDiscovery();

            getPairedDevices();
            registerBroadcastReceiver();

            IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST);
            intentFilter.setPriority(Integer.MAX_VALUE);
            getActivity().registerReceiver(new BluetoothConnectActivityReceiver(), intentFilter);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void scanLeDevice(final boolean enable) {
        mBluetoothAdapter.startLeScan(mLeScanCallback);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }, 10000);
    }


    // 设备搜索回调接口.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi,
                                     byte[] scanRecord) {
                    String name = "";
                    if (device != null) {
                        name = device.getName();
                    }
                    name = name == null ? "null" : name;
                    Log.e(TAG, name);
                }
            };


    private void getPairedDevices() {
        for (BluetoothDevice device : mBluetoothAdapter.getBondedDevices()) {
            Log.e(TAG, "已配对" + device.getName());
            mBluetoothDevices.add(device);
            if(device.getName().equals("Softwinner-mars-a31s"))
            {
                target = device;
            }
        }
    }

    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        getActivity().registerReceiver(mBroadcastReceiver, filter);
    }

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    device.getAddress();
                    Log.e(TAG, "未配对 " + (device.getName() == null ? "" : device.getName()));
                }
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                Log.e(TAG, "扫描完成");
            }
        }
    };

    public void sendTest2Server() {
        if (target != null) {
            OutputStream op = null;
            try {
                if(clientSocket == null)
                {
                    BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(target.getAddress());
                    clientSocket = bluetoothDevice.createRfcommSocketToServiceRecord(TraditionalServerFragment.UUID);
                    clientSocket.connect();
                    op = clientSocket.getOutputStream();
                }

                if (op != null) {
                    op.write("测试蓝牙哈哈哈".getBytes("utf-8"));
                    op.flush();
                    Toast.makeText(getActivity(), "请求成功..", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
//                try {
//                    if (op != null) {
//                        op.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }

    }

    /**
     * 拦截广播,静默配对
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public class BluetoothConnectActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BluetoothDevice.ACTION_PAIRING_REQUEST)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.e(TAG, "请求配对: "+device.getName());

                if(device.getName().equals("Softwinner-mars-a31s"))
                {
                    target = device;
                }
                try {
                    // 广播接收者的优先者为最高 收到广播后 停止广播向下传递
                    // TODO: 16/8/9 判断device是否是自己的设备，是的话拦截，否则不处理。
                    device.setPairingConfirmation(true);
                    abortBroadcast();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick(R.id.hello)
    public void clickHello()
    {
        sendTest2Server();
    }
}
