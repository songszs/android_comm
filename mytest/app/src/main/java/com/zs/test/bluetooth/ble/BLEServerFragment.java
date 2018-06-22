package com.zs.test.bluetooth.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-21 下午5:53
 * @description: mytest
 */
public class BLEServerFragment extends BaseFragment {

    //    // 扫描结果Callback
    //    private final ScanCallback mScanCallback = new ScanCallback() {
    //        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    //        @Override
    //        public void onScanResult(int callbackType, ScanResult result) {
    //            //获取BLE设备信息
    //            BluetoothDevice dev = result.getDevice();
    //            // result.getScanRecord() 获取BLE广播数据
    //        }
    //    };


    private static final String TAG = "BLEClientFragment";

    private BluetoothAdapter mBluetoothAdapter;
    private Handler          mHandler;
    private BluetoothDevice  targetDevice;
    private BluetoothGatt    mBluetoothGatt;

    public static ParcelUuid SERVER_UUID  = ParcelUuid.fromString("12345678-abcd-1000-8000-123456000000");
    public static UUID       SERVER_UUID_ = UUID.fromString("12345678-abcd-1000-8000-123456000000");
    public static ParcelUuid D_UUID       = ParcelUuid.fromString("12345678-abcd-1000-8000-123456000001");

    @Override
    protected int createViewId() {
        return R.layout.main;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        mHandler = new Handler(Looper.getMainLooper());
        scanBle();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void scanBle() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Log.e(TAG, "开始扫描...");
        mBluetoothAdapter.startLeScan(mLeScanCallback);
        //        mBluetoothAdapter.startLeScan(mLeScanCallback);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "停止扫描...");
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }, 30000);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void connectHost() {
        if (targetDevice != null) {
            mBluetoothGatt = targetDevice.connectGatt(getActivity(), true, mBluetoothGattCallback);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void readUUidData(UUID serviceUid, UUID charactersticUid) {
        if (mBluetoothGatt != null) {
            BluetoothGattService        service        = mBluetoothGatt.getService(serviceUid);
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(charactersticUid);

            byte[] conteneBytes = characteristic.getValue();

            try {
                Log.e(TAG, new String(conteneBytes, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(descriptorUid);
            //            descriptor.getValue();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void writeUUidData(UUID serviceUid, UUID charactersticUid) {
        if (mBluetoothGatt != null) {
            BluetoothGattService        service        = mBluetoothGatt.getService(serviceUid);
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(charactersticUid);
            characteristic.setValue("写入数据");
            mBluetoothGatt.writeCharacteristic(characteristic);
        }
        //            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(descriptorUid);
        //            descriptor.getValue();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void closeGatt() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
        }
    }


    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (device.getName() != null) {
                Log.e(TAG, "发现设备: " + device.getName());
                if (device.getUuids() != null) {
                    for (ParcelUuid uuid : device.getUuids()) {
                        Log.e(TAG, device.getName() + " uuid" + uuid);
                    }
                }
                printScanRecord(scanRecord);
                if (device.getName().equals("")) {
                    targetDevice = device;

                    connectHost();
                }
            }
        }
    };

    public BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {

        @Override
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
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
            else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.e(TAG, "断开连接");
                gatt.close();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.e(TAG, "发现Services");
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }
    };


    public void printScanRecord(byte[] scanRecord) {
        // Simply print all raw bytes
        try {
            String decodedRecord = new String(scanRecord, "UTF-8");
            Log.d("DEBUG", "decoded String : " + ByteArrayToString(scanRecord));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Parse data bytes into individual records
        List<AdRecord> records = AdRecord.parseScanRecord(scanRecord);


        // Print individual records
        if (records.size() == 0) {
            Log.i("DEBUG", "Scan Record Empty");
        } else {
            Log.i("DEBUG", "Scan Record: " + TextUtils.join(",", records));
        }

    }


    public static String ByteArrayToString(byte[] ba) {
        StringBuilder hex = new StringBuilder(ba.length * 2);
        for (byte b : ba) {
            hex.append(b + " ");
        }
        return hex.toString();
    }

    public static class AdRecord {

        public AdRecord(int length, int type, byte[] data) {
            String decodedRecord = "";
            try {
                decodedRecord = new String(data, "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.d("DEBUG", "Length: " + length + " Type : " + type + " Data : " + ByteArrayToString(data));
        }

        // ...

        public static List<AdRecord> parseScanRecord(byte[] scanRecord) {
            List<AdRecord> records = new ArrayList<AdRecord>();

            int index = 0;
            while (index < scanRecord.length) {
                int length = scanRecord[index++];
                //Done once we run out of records
                if (length == 0)
                    break;

                int type = scanRecord[index];
                //Done if our record isn't a valid type
                if (type == 0)
                    break;

                byte[] data = Arrays.copyOfRange(scanRecord, index + 1, index + length);

                records.add(new AdRecord(length, type, data));
                //Advance
                index += length;
            }

            return records;
        }
    }
}
