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
import android.util.Log;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
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

    public static UUID SERVER_UUID_READ  = UUID.fromString("00007777-0000-1000-8000-00805f9b34fb");
    public static UUID SERVER_UUID_WRITE = UUID.fromString("11212315-1111-2222-3333-00805f9b34fb");
    public static UUID D_UUID_READ       = UUID.fromString("00007778-0000-1000-8000-00805f9b34fb");
    public static UUID D_UUID_WRITE      = UUID.fromString("00007779-0000-1000-8000-00805f9b34fb");

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
                ParsedAd ad = parseData(scanRecord);
                Log.e(TAG, ad.toString());
                for (UUID uuid : ad.uuids) {
                    if (uuid.equals(SERVER_UUID_READ)) {
                        Log.e(TAG, "找到设备，准备连接...");
                        targetDevice = device;
                        connectHost();
                        break;
                    }
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
                Log.e(TAG, "停止扫描...");
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
            //断开连接
            else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.e(TAG, "断开连接");
                gatt.close();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            gatt.getServices();
            Log.e(TAG, "发现Services start");
            for (BluetoothGattService service : gatt.getServices()) {
                Log.e(TAG, "service :" + service.getUuid());
            }
            Log.e(TAG, "发现Services end");

            //从ble读内容
            BluetoothGattService        gattService    = gatt.getService(SERVER_UUID_READ);
            if(gattService != null)
            {
                BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(D_UUID_READ);
                gatt.readCharacteristic(characteristic);
//

                //往ble写内容
//                BluetoothGattCharacteristic characteristicWrite = gattService.getCharacteristic(D_UUID_WRITE);
//                gatt.setCharacteristicNotification(characteristicWrite, true);
//                characteristicWrite.setValue("测试server");
//                characteristicWrite.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
//                gatt.writeCharacteristic(characteristicWrite);

                // 依据协议订阅相关信息,否则接收不到数据
                gatt.setCharacteristicNotification(characteristic, true);
                for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    gatt.writeDescriptor(descriptor);
                }

//                for (BluetoothGattDescriptor descriptor : characteristicWrite.getDescriptors()) {
//                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//                    gatt.writeDescriptor(descriptor);
//                }
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.e(TAG, "onCharacteristicRead " + new String(characteristic.getValue()));
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.e(TAG, "onCharacteristicRead " + characteristic.getUuid());
            if (characteristic.getValue() != null) {
                Log.e(TAG, "onCharacteristicRead " + new String(characteristic.getValue()));
            } else {
                Log.e(TAG, "onCharacteristicRead " + characteristic.getValue().length);
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.e(TAG, "onCharacteristicWrite " + status);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.e(TAG, "onCharacteristicWrite " + status);
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


    /**
     * 解析广播数据
     *
     * @param adv_data
     * @return
     */
    public static ParsedAd parseData(byte[] adv_data) {
        ParsedAd   parsedAd = new ParsedAd();
        ByteBuffer buffer   = ByteBuffer.wrap(adv_data).order(ByteOrder.LITTLE_ENDIAN);
        while (buffer.remaining() > 2) {
            byte length = buffer.get();
            if (length == 0)
                break;

            byte type = buffer.get();
            length -= 1;
            switch (type) {
                case 0x01: // Flags
                    parsedAd.flags = buffer.get();
                    length--;
                    break;
                case 0x02: // Partial list of 16-bit UUIDs
                case 0x03: // Complete list of 16-bit UUIDs
                case 0x14: // List of 16-bit Service Solicitation UUIDs
                    while (length >= 2) {
                        parsedAd.uuids.add(UUID.fromString(String.format(
                                "%08x-0000-1000-8000-00805f9b34fb", buffer.getShort())));
                        //                        byte[] bytes       = new byte[length];
                        //                        buffer.get(bytes, 0, bytes.length);
                        //                        parsedAd.uuids.add(UUID.nameUUIDFromBytes(bytes));
                        length -= 2;
                    }
                    break;
                case 0x04: // Partial list of 32 bit service UUIDs
                case 0x05: // Complete list of 32 bit service UUIDs
                    while (length >= 4) {
                        parsedAd.uuids.add(UUID.fromString(String.format(
                                "%08x-0000-1000-8000-00805f9b34fb", buffer.getInt())));
                        //                        byte[] bytes       = new byte[length];
                        //                        buffer.get(bytes, 0, bytes.length);
                        //                        parsedAd.uuids.add(UUID.nameUUIDFromBytes(bytes));
                        length -= 4;
                    }
                    break;
                case 0x06: // Partial list of 128-bit UUIDs
                case 0x07: // Complete list of 128-bit UUIDs
                case 0x15: // List of 128-bit Service Solicitation UUIDs
                    while (length >= 16) {
                        long lsb = buffer.getLong();
                        long msb = buffer.getLong();
                        parsedAd.uuids.add(new UUID(msb, lsb));
                        length -= 16;
                    }
                    break;
                case 0x08: // Short local device name
                case 0x09: // Complete local device name
                    byte sb[] = new byte[length];
                    buffer.get(sb, 0, length);
                    length = 0;
                    parsedAd.localName = new String(sb).trim();
                    break;
                case (byte) 0xFF: // Manufacturer Specific Data
                    parsedAd.manufacturer = buffer.getShort();
                    length -= 2;
                    break;
                default: // skip
                    break;
            }
            if (length > 0) {
                buffer.position(buffer.position() + length);
            }
        }
        return parsedAd;
    }


    public static final class ParsedAd {
        String     localName;
        List<UUID> uuids;
        byte       flags;
        short      manufacturer;


        public ParsedAd() {
            uuids = new ArrayList<>();
        }

        public String getLocalName() {
            return localName;
        }

        public void setLocalName(String localName) {
            this.localName = localName;
        }

        public List<UUID> getUuids() {
            return uuids;
        }

        public void setUuids(List<UUID> uuids) {
            this.uuids = uuids;
        }

        public byte getFlags() {
            return flags;
        }

        public void setFlags(byte flags) {
            this.flags = flags;
        }

        public short getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(short manufacturer) {
            this.manufacturer = manufacturer;
        }

        @Override
        public String toString() {
            return "ParsedAd{" +
                   "localName='" + localName + '\'' +
                   ", uuids=" + uuids +
                   ", flags=" + flags +
                   ", manufacturer=" + manufacturer +
                   '}';
        }
    }
}
