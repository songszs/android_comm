package com.zs.test.bluetooth.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ;
import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_WRITE;
import static android.bluetooth.BluetoothGattCharacteristic.PROPERTY_BROADCAST;
import static android.bluetooth.BluetoothGattCharacteristic.PROPERTY_INDICATE;
import static android.bluetooth.BluetoothGattCharacteristic.PROPERTY_NOTIFY;
import static android.bluetooth.BluetoothGattCharacteristic.PROPERTY_READ;
import static android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE;
import static com.zs.test.bluetooth.ble.BLEServerFragment.D_UUID_READ;
import static com.zs.test.bluetooth.ble.BLEServerFragment.D_UUID_WRITE;
import static com.zs.test.bluetooth.ble.BLEServerFragment.SERVER_UUID_READ;


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

    BluetoothGattServer gattServer;


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
                Log.e(TAG, "onStartFailure " + errorCode);
            }
        };

        //开启服务
        openServer();

        //发送广播
        sendBrocast();

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openServer() {
        BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager != null) {
            gattServer = bluetoothManager.openGattServer(getActivity(), gattServerCallback);

            BluetoothGattService service = new BluetoothGattService(SERVER_UUID_READ, BluetoothGattService.SERVICE_TYPE_PRIMARY);

            //添加一个可读的Characteristic
            BluetoothGattCharacteristic characteristic1 = new BluetoothGattCharacteristic(
                    D_UUID_READ,
                    PROPERTY_WRITE | PROPERTY_READ | PROPERTY_BROADCAST | PROPERTY_NOTIFY | PROPERTY_INDICATE,
                    PERMISSION_WRITE | PERMISSION_READ);
            //添加一个可写的Characteristic
            BluetoothGattCharacteristic characteristic2 = new BluetoothGattCharacteristic(
                    D_UUID_WRITE,
                    PROPERTY_WRITE | PROPERTY_READ | PROPERTY_NOTIFY | PROPERTY_INDICATE,
                    PERMISSION_WRITE);

            service.addCharacteristic(characteristic1);
            service.addCharacteristic(characteristic2);
            gattServer.addService(service);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void sendBrocast() {
        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();

        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER)
                .setConnectable(true)
                .build();
        AdvertiseData data = new AdvertiseData.Builder()
                .addServiceUuid(new ParcelUuid(SERVER_UUID_READ))
                .setIncludeDeviceName(true)
                .addManufacturerData(0x7777, new byte[]{0x07, 0x07})
                .build();

        ////        扫描响应数据(可选，当客户端扫描时才发送)
        //        AdvertiseData scanResponse = new AdvertiseData.Builder()
        ////                设备厂商数据，自定义
        //                .addManufacturerData(2, new byte[]{66, 66})
        ////                服务UUID
        //                .addServiceUuid(new ParcelUuid(SERVER_UUID_))
        //                      .addServiceData(new ParcelUuid(UUID_SERVICE), new byte[]{2}) //服务数据，自定义
        //                .build();

        mBluetoothLeAdvertiser.startAdvertising(settings, data, mAdvertiseCallback);
    }


    private BluetoothGattServerCallback gattServerCallback = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(final BluetoothDevice device, int status, int newState) {
            super.onConnectionStateChange(device, status, newState);

            Log.e(TAG, " onConnectionStateChange:" + status + " newState:" + newState + " devicename:" + device.getName() + " mac:" + device.getAddress());

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onServiceAdded(int status, BluetoothGattService service) {
            super.onServiceAdded(status, service);

            Log.e(TAG, " onServiceAdded status:" + status + " service:" + service.getUuid().toString());
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
            Log.e(TAG, " onCharacteristicReadRequest requestId:" + requestId + " offset:" + offset + " characteristic:" + characteristic.getUuid().toString());

            gattServer.sendResponse(device, requestId, 0, offset, "hello".getBytes());
            gattServer.sendResponse(device, requestId, 0, offset, "hello2".getBytes());
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
            Log.e(TAG, " onCharacteristicWriteRequest requestId:" + requestId + " preparedWrite:" + preparedWrite + " responseNeeded:" + responseNeeded + " offset:" + offset + " value:" + " characteristic:" + characteristic.getUuid().toString());
            gattServer.sendResponse(device, requestId, 0, offset, value);


            characteristic.setValue("发给客户端的新信息");

            gattServer.notifyCharacteristicChanged(device,characteristic,true);
//            if (value != null) {
//                Toast.makeText(getActivity(), new String(value), Toast.LENGTH_LONG).show();
//                Log.e(TAG, new String(value));
//            }
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
            super.onDescriptorReadRequest(device, requestId, offset, descriptor);

            Log.e(TAG, " onCharacteristicReadRequest requestId:" + requestId + " offset:" + offset + " descriptor:" + descriptor.getUuid().toString());


        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onDescriptorWriteRequest(final BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);
            Log.e(TAG, " onDescriptorWriteRequest requestId:" + requestId + " preparedWrite:" + preparedWrite + " responseNeeded:" + responseNeeded + " offset:" + offset + " value:" + " characteristic:" + descriptor.getUuid().toString());
            // now tell the connected device that this was all successfull
            gattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
        }

        @Override
        public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
            super.onExecuteWrite(device, requestId, execute);

            Log.e(TAG, " onExecuteWrite requestId:" + requestId + " execute:" + execute);
        }

        @Override
        public void onNotificationSent(BluetoothDevice device, int status) {
            super.onNotificationSent(device, status);

            Log.e(TAG, " onNotificationSent status:" + status);
        }

        @Override
        public void onMtuChanged(BluetoothDevice device, int mtu) {
            super.onMtuChanged(device, mtu);
            Log.e(TAG, " onMtuChanged mtu:" + mtu);
        }
    };


    /**
     * 4.处理响应内容
     *
     * @param reqeustBytes
     * @param device
     * @param requestId
     * @param characteristic
     */
    private void onResponseToClient(byte[] reqeustBytes, BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic) {
        Log.e(TAG, String.format("4.onResponseToClient：device name = %s, address = %s", device.getName(), device.getAddress()));
        Log.e(TAG, String.format("4.onResponseToClient：requestId = %s", requestId));
//        String msg = OutputStringUtil.transferForPrint(reqeustBytes);
//
//        String str = new String(reqeustBytes) + " hello>";
//        characteristicRead.setValue(str.getBytes());
//        bluetoothGattServer.notifyCharacteristicChanged(device, characteristicRead, false);

    }

}
