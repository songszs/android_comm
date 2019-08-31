package com.zs.test.bluetooth.fastble;

import android.util.Log;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.scan.BleScanRuleConfig;

import java.util.List;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2019-08-19 16:47
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class BleConfigNetManager {

    private static BleConfigNetManager instance = BleManagerHolder.instance;

    private static class BleManagerHolder {
        private static final BleConfigNetManager instance = new BleConfigNetManager();
    }


    public static BleConfigNetManager getInstance() {
        return instance;
    }


    public void scan() {


        BleManager.getInstance().enableBluetooth();

        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                // 只扫描指定的服务的设备，可选
//                .setServiceUuids(new UUID[]{UUID_SSID})
                //                .setDeviceName(true, names)         // 只扫描指定广播名的设备，可选
                //                .setDeviceMac(mac)                  // 只扫描指定mac的设备，可选
                .setAutoConnect(false)      // 连接时的autoConnect参数，可选，默认false
                // 扫描超时时间，可选，默认10秒
                .setScanTimeOut(10000)
                .build();

        BleManager.getInstance().initScanRule(scanRuleConfig);
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                Log.e("ble test", "ble onScanFinished");
                for (BleDevice bleDevice : scanResultList) {
                    if (bleDevice.getName() != null && bleDevice.getName().contains("Gowild_JG_6D5420")) {
                        BleConfigNetDev bleConfigNetDev = new BleConfigNetDev(bleDevice);
                        bleConfigNetDev.configNet(null, null, new BleConfigNetDev.OnConfigNetResultCallback() {
                            @Override
                            public void onConfigNetResult(boolean result) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onScanStarted(boolean success) {
                Log.e("ble test", "ble onScanStarted");

            }

            @Override
            public void onScanning(BleDevice bleDevice) {

                if (bleDevice.getName() != null && bleDevice.getName().contains("Gowild_JG_6D5420")) {
                    Log.e("ble onScanning", "ble dev " + bleDevice.getMac() + bleDevice.getName());

                    BleManager.getInstance().cancelScan();
                }

            }
        });
    }
}

