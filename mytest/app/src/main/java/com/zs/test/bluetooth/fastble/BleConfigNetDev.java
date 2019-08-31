package com.zs.test.bluetooth.fastble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleMtuChangedCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2019-08-19 15:54
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class BleConfigNetDev {


    public static final UUID UUID_SSID = UUID.fromString("00000180a-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_PWD = UUID.fromString("00009999-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_RESULT = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private BleDevice bleDevice;

    private BleGattCallback gattCallback;

    private OnConfigNetResultCallback callback;

    private BluetoothGatt gatt;

    //接受buf
    private ByteBuffer recvBuf;

    //机器端一次最大发送数据量
    private final int MTU = 503;

    //需要接受的次数
    private int needRecvTimes;
    //已经接受的次数
    private int hadRecvTimes;


    public BleConfigNetDev(BleDevice bleDevice) {
        this.bleDevice = bleDevice;
        gattCallback = new BleGattCallback() {
            @Override
            public void onStartConnect() {
                Log.e("ble test", "ble onStartConnect");

            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                Log.e("ble test", "ble onConnectFail");
                Log.e("ble test", exception.toString());

            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                BleConfigNetDev.this.gatt = gatt;
                Log.e("ble test", "ble onConnectSuccess");

//                List<BluetoothGattService> serviceList = BleManager.getInstance().getBluetoothGattServices(bleDevice);
//
//                for (BluetoothGattService service : serviceList) {
//                    Log.e("ble test", "ble BluetoothGattService " + service.getUuid().toString());
//                }

                setMtu();


            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
                Log.e("ble test", "ble onDisConnected");

            }
        };
    }

    public void configNet(String ssid, String pwd, OnConfigNetResultCallback cb) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                BleManager.getInstance().connect(bleDevice, gattCallback);
            }
        }, 1000);
    }

    private void read() {
        BleManager.getInstance().read(bleDevice, UUID_SSID.toString(), UUID_PWD.toString(), new BleReadCallback() {
            @Override
            public void onReadSuccess(byte[] data) {
                Log.e("ble onReadSuccess", "ble " + new String(data));
                BleManager.getInstance().disconnectAllDevice();
            }

            @Override
            public void onReadFailure(BleException exception) {
                Log.e("ble onReadFailure", "ble onReadFailure " + exception);
                BleManager.getInstance().disconnectAllDevice();
            }
        });
    }


    private void write() {
        String json = "{\n" +
                "    \"wifi_ssid\": \"Gowild_Test\", \n" +
                "    \"wifi_pwd\": \"helloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworldhelloworld\", \n" +
//                "    \"wifi_pwd\": \"123456\", \n" +
                "    \"account\": \"13688886666\"\n" +
                "}";
//        String json = "hello world";
        byte[] data = getProtol(json, JGBleCmd.NET_BIND_CONFIG);
        BleManager.getInstance().write(bleDevice, UUID_SSID.toString(), UUID_PWD.toString(), data, new BleWriteCallback() {
            @Override
            public void onWriteSuccess(int current, int total, byte[] justWrite) {
                Log.e("ble onWriteSuccess", "ble write success");

            }

            @Override
            public void onWriteFailure(BleException exception) {
                Log.e("ble onWriteFailure", "ble write onWriteFailure " + exception);
                BleManager.getInstance().disconnectAllDevice();
            }
        });

    }


    public void notifyData() {
        BleManager.getInstance().notify(
                bleDevice,
                UUID_SSID.toString(), UUID_PWD.toString(),
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        // 打开通知操作成功
                        Log.e("ble test", "ble onNotifySuccess");

                        write();
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        // 打开通知操作失败
                        Log.e("ble test", "ble onNotifyFailure " + exception);

                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        // 打开通知后，设备发过来的数据将在这里出现
                        Log.e("ble test", "ble onCharacteristicChanged ");
                        handleRecv(data);
                    }
                });

    }


    public void setMtu() {
        BleManager.getInstance().setMtu(bleDevice, MTU, new BleMtuChangedCallback() {
            @Override
            public void onSetMTUFailure(BleException exception) {
                // 设置MTU失败
                Log.e("ble test", "ble onSetMTUFailure");

            }

            @Override
            public void onMtuChanged(int mtu) {
                // 设置MTU成功，并获得当前设备传输支持的MTU值
                Log.e("ble test", "ble onMtuChanged");
                notifyData();
            }
        });

    }


    public byte[] getProtol(String data, JGBleCmd cmd) {
        byte[] db = data.getBytes();
        int protolLen = db.length + 6;
        ByteBuffer protol = ByteBuffer.allocate(protolLen);

        //头
        protol.put((byte) 0xAA);

        //长度
        protol.putShort((short) db.length);

        //指令
        protol.putShort(cmd.getCmd());

        //数据
        protol.put(db);

        //校验和
        byte checkNum = 0;
        for (int i = 1; i < protolLen - 1; i++) {
            checkNum ^= protol.get(i);
        }
        protol.put(checkNum);

        return protol.array();

    }

    public void handleRecv(byte[] data) {
        Log.e("ble test", "recv msg len " + data.length);

        if (data.length < 6) {
            return;
        }
        ByteBuffer tmpBuf = ByteBuffer.allocate(data.length);
        tmpBuf.put(data);
        tmpBuf.position(0);
        //如果是消息头 申请buf并计算一共要接受几次
        if (tmpBuf.get() == (byte) 0xAA) {
            short len = tmpBuf.getShort();
            int msgLen = len + 6;

            Log.e("ble test", "recv1 msg len " + msgLen);
            final int MTU_MSG_LEN = MTU - 3;

            needRecvTimes = msgLen / MTU_MSG_LEN + 1;

            //创建新的buf
            recvBuf = ByteBuffer.allocate(msgLen);
            hadRecvTimes = 0;
        }
        if (recvBuf != null) {
            //添加数据
            recvBuf.put(data);
            hadRecvTimes++;
        }

        if (needRecvTimes == hadRecvTimes) {
            Log.e("ble test", "接受到所有数据 解析");

            parse(recvBuf);
        } else {
            Log.e("ble test", "未接受到完整数据 needRecvTimes " + needRecvTimes + " hadRecvTimes " + hadRecvTimes);
        }
    }


    public void parse(ByteBuffer protol) {

        if (!check(protol.array())) {
            Log.e("ble test", "校验失败");
            return;
        }
        Log.e("ble test", "校验成功");

        //回到头
        protol.position(0);
        //读头
        byte head = protol.get();
        //读数据长度
        short dataLen = protol.getShort();
        //读指令
        short cmd = protol.getShort();
        for (JGBleCmd jgBleCmd : JGBleCmd.values()) {
            if (jgBleCmd.getCmd() == cmd) {
                Log.e("ble test", "解析 cmd " + jgBleCmd.getDesc());
                break;
            }
        }
        //读内容
        byte[] content = new byte[dataLen];
        protol.get(content);
        Log.e("ble test", "解析 content" + new String(content));
    }


    private boolean check(byte[] data) {
        if (data.length <= 6) {
            return false;
        }
        byte fcs = data[data.length - 1];
        byte checkNum = 0;
        for (int i = 1; i < data.length - 1; i++) {
            checkNum ^= data[i];
        }
        return fcs == checkNum;
    }


    public static interface OnConfigNetResultCallback {
        void onConfigNetResult(boolean result);
    }


}
