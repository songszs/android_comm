package com.zs.test.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-21 上午11:21
 * @description: mytest
 */
public class InvokeUitls {

    public static boolean invokeMethod(Object object)
    {
        try {
            Method setMode = object.getClass().getDeclaredMethod("setScanMode", int.class, int.class);
            setMode.invoke(object, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE, 300);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;
    }
}
