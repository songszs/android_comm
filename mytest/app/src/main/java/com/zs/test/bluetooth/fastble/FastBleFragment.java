package com.zs.test.bluetooth.fastble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothProfile;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleIndicateCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.zs.R;
import com.zs.base.view.BaseFragment;

import java.util.List;
import java.util.UUID;

import static com.zs.test.bluetooth.ble.BLEServerFragment.D_UUID_READ;
import static com.zs.test.bluetooth.ble.BLEServerFragment.D_UUID_WRITE;
import static com.zs.test.bluetooth.ble.BLEServerFragment.SERVER_UUID_READ;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-26 下午3:46
 * @description: mytest
 */
public class FastBleFragment extends BaseFragment {

    private static final String TAG = "FastBleFragment";

    BleDevice bleDevice;
    private String uuid_service                 = SERVER_UUID_READ.toString();
    private String uuid_characteristic_notify   = D_UUID_WRITE.toString();
    private String uuid_characteristic_indicate = D_UUID_WRITE.toString();
    private String uuid_characteristic_read     = D_UUID_READ.toString();
    private String uuid_characteristic_write    = D_UUID_WRITE.toString();

    @Override
    protected int createViewId() {
        return R.layout.main;
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        BleManager.getInstance().init(getActivity().getApplication());
        BleManager.getInstance().setConnectOverTime(120000);
        //                  .enableLog(true)
        //                  .setReConnectCount(5, 5000)
        //                  .setOperateTimeout(5000);

        BleManager.getInstance().enableBluetooth();
        scan();

    }

    private void scan() {
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                // 只扫描指定的服务的设备，可选
                .setServiceUuids(new UUID[]{SERVER_UUID_READ})
                //                .setDeviceName(true, names)         // 只扫描指定广播名的设备，可选
                //                .setDeviceMac(mac)                  // 只扫描指定mac的设备，可选
                .setAutoConnect(true)      // 连接时的autoConnect参数，可选，默认false
                // 扫描超时时间，可选，默认10秒
                .setScanTimeOut(5000)
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                Log.e(TAG, "onScanStarted ");
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onLeScan(BleDevice device) {
                Log.e(TAG, "onLeScan 设备名字 :" + device.getName());
                BleManager.getInstance().cancelScan();
                bleDevice = device;

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        connect();
                    }
                }, 500);

                //                final BluetoothGatt bluetoothGatt = bleDevice.getDevice().connectGatt(getActivity().getApplication(), true, mBluetoothGattCallback);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (bleDevice.getDevice() != null) {
                            if (BleManager.getInstance().getBluetoothGatt(bleDevice) != null) {
                                BleManager.getInstance().getBluetoothGatt(bleDevice).disconnect();
                                BleManager.getInstance().getBluetoothGatt(bleDevice).close();
                                Log.e(TAG, "getBluetoothGatt disconnect ");
                            }
                        }
                    }
                }, 120000);
            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                Log.e(TAG, "onScanning 设备名字 :" + bleDevice.getName());
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                Log.e(TAG, "onScanFinished 设备名字 :" + scanResultList.size());
            }
        });
    }


    private void connect() {
        BleManager.getInstance().connect(bleDevice, new BleGattCallback() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);
                //GATT建立成功
                if (BluetoothGatt.GATT_SUCCESS == status) {
                    Log.e(TAG, "GATT建立成功");
                }
                //连接成功
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.e(TAG, "设备连接成功");
                    gatt.discoverServices();
                }
                //断开连接
                else {
                    Log.e(TAG, "断开连接");
                    gatt.close();
                }
            }

            @Override
            public void onStartConnect() {
                Log.e(TAG, "onStartConnect ");
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                Log.e(TAG, "onConnectFail " + exception.toString());
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                Log.e(TAG, "onConnectSuccess ");
                openIndicate();
                read();
                //                openNotify();
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                Log.e(TAG, "onDisConnected ");
            }
        });
    }

    private void openNotify() {
        BleManager.getInstance().notify(
                bleDevice,
                uuid_service,
                uuid_characteristic_notify, new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        Log.e(TAG, "onNotifySuccess ");
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        Log.e(TAG, "onNotifyFailure ");
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        Log.e(TAG, "onCharacteristicChanged ");
                    }
                });
    }

    private void closeNotify() {
        BleManager.getInstance().stopNotify(bleDevice, uuid_service, uuid_characteristic_notify);
    }

    private void openIndicate() {
        BleManager.getInstance().indicate(
                bleDevice,
                uuid_service,
                uuid_characteristic_indicate,
                new BleIndicateCallback() {
                    @Override
                    public void onIndicateSuccess() {
                        // 打开通知操作成功
                        Log.e(TAG, "onIndicateSuccess ");
                        read();
                    }

                    @Override
                    public void onIndicateFailure(BleException exception) {
                        // 打开通知操作失败
                        Log.e(TAG, "onIndicateFailure " + exception.toString());
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        // 打开通知后，设备发过来的数据将在这里出现
                        Log.e(TAG, "onCharacteristicChanged " + new String(data));
                    }
                });
    }

    private void closeIndicate() {
        BleManager.getInstance().stopIndicate(bleDevice, uuid_service, uuid_characteristic_indicate);
    }


    private void read() {
        BleManager.getInstance().read(
                bleDevice,
                uuid_service,
                uuid_characteristic_read,
                new BleReadCallback() {
                    @Override
                    public void onReadSuccess(byte[] data) {
                        // 读特征值数据成功
                        Log.e(TAG, "onReadSuccess " + new String(data));
                        wirte();
                    }

                    @Override
                    public void onReadFailure(BleException exception) {
                        Log.e(TAG, "onReadFailure " + exception.toString());
                        // 读特征值数据失败
                    }
                });
    }


    private void wirte() {
        byte[] data = "can i wirte".getBytes();
        BleManager.getInstance().write(
                bleDevice,
                uuid_service,
                uuid_characteristic_write,
                data,
                new BleWriteCallback() {
                    @Override
                    public void onWriteSuccess(int current, int total, byte[] justWrite) {
                        // 发送数据到设备成功（分包发送的情况下，可以通过方法中返回的参数可以查看发送进度）
                        Log.e(TAG, "onWriteSuccess " + current);
                        Log.e(TAG, "onWriteSuccess " + total);

                    }

                    @Override
                    public void onWriteFailure(BleException exception) {
                        // 发送数据到设备失败
                        Log.e(TAG, "onWriteFailure " + exception.toString());
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (BleManager.getInstance().getBluetoothGatt(bleDevice) != null) {
            BleManager.getInstance().getBluetoothGatt(bleDevice).disconnect();
            BleManager.getInstance().getBluetoothGatt(bleDevice).close();
            Log.e(TAG, "getBluetoothGatt disconnect ");
        }
    }
}
