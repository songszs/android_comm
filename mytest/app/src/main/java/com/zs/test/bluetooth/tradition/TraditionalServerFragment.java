package com.zs.test.bluetooth.tradition;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.zs.R;
import com.zs.base.view.BaseFragment;
import com.zs.test.bluetooth.InvokeUitls;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-20 下午4:59
 * @description: mytest
 */
public class TraditionalServerFragment extends BaseFragment {

    protected Handler handler;
    BluetoothAdapter tmBluetoothAdapter;
    static final String MY_UUID = "abcd1234-ab12-ab12-ab12-abcdef123456";
    static final String TAG     = "BlueToothServerFragment";
    static final String NAME    = "com.bluetooth.demo";
    static final UUID   UUID    = java.util.UUID.fromString(MY_UUID);
    BluetoothSocket socket;

    ExecutorService mExecutorService;

    @Override
    protected int createViewId() {
        return R.layout.activity_main;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 3:
                        Log.e(TAG, "content: " + msg.obj);
                        EditText editText = (EditText)(getActivity().findViewById(R.id.editText));
                        editText.setText(msg.obj.toString());
                        break;
                    default:
                        break;
                }
            }
        };

        mExecutorService = Executors.newSingleThreadExecutor();

        // 初始化蓝牙适配器.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        tmBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        tmBluetoothAdapter.enable();

        Log.e(TAG, tmBluetoothAdapter.getName());
        Log.e(TAG, tmBluetoothAdapter.getAddress());

        InvokeUitls.invokeMethod(tmBluetoothAdapter);
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    startServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST);
        intentFilter.setPriority(Integer.MAX_VALUE);
        getActivity().registerReceiver(new BluetoothConnectActivityReceiver(), intentFilter);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void startServer() throws IOException {

        //服务器端的bltsocket需要传入uuid和一个独立存在的字符串，以便验证，通常使用包名的形式
        BluetoothServerSocket bluetoothServerSocket = tmBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, UUID);
        while (true) {
            try {
                //注意，当accept()返回BluetoothSocket时，socket已经连接了，因此不应该调用connect方法。
                //这里会线程阻塞，直到有蓝牙设备链接进来才会往下走
                Log.e(TAG,"等待监听...");
                socket = bluetoothServerSocket.accept();
                if (socket != null) {
                    Log.e(TAG,"有新的连接...");
                    //回调结果通知
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    InputStream           is           = socket.getInputStream();
                    byte[]                buffer       = new byte[1024];
                    int                   length;
                    length = is.read(buffer);
//                    while ((length = is.read(buffer)) != -1) {
//                        outputStream.write(buffer, 0, length);
//                        Log.e(TAG,"读取...");
//                    }
//                    String content = outputStream.toString(StandardCharsets.UTF_8.name());
//                    Log.e(TAG,"获取到content.. "+content);
                    Message message = handler.obtainMessage();
                    message.what = 3;
                    message.obj = new String(buffer,0,length,"utf-8");
                    handler.sendMessage(message);
                    //如果你的蓝牙设备只是一对一的连接，则执行以下代码
                    bluetoothServerSocket.close();
                    //如果你的蓝牙设备是一对多的，则应该调用break；跳出循环
                    //break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    bluetoothServerSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
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
                Log.e(TAG, "请求配对");
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
}
