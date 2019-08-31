package com.zs.base.router;

import android.content.Context;

import com.zs.base.view.CommonFragmentActivity;
import com.zs.test.andfix.AndFixFragment;
import com.zs.test.annotation.generate.GenerateCodeFragment;
import com.zs.test.aspect.AspectFragment;
import com.zs.test.bluetooth.ble.BLEClientFragment;
import com.zs.test.bluetooth.ble.BLEServerFragment;
import com.zs.test.bluetooth.fastble.BleConfigNetFragment;
import com.zs.test.bluetooth.fastble.FastBleFragment;
import com.zs.test.bluetooth.tradition.TraditionalClientFragment;
import com.zs.test.bluetooth.tradition.TraditionalServerFragment;
import com.zs.test.butterknife.ButterKnifeFragment;
import com.zs.test.eventbus.EventBusFragment;
import com.zs.test.eventbus.EventBusFragment2;
import com.zs.test.glide.GlideFragment;
import com.zs.test.nio.NioFragment;
import com.zs.test.okhttp.OkHttpFragment;
import com.zs.test.retrofit.RetrofitFragment;
import com.zs.test.rxbus.RxbusFragment;
import com.zs.test.thread.ThreadFragment;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-4 下午5:01
 * @description: mytest
 */
public class AuthRouterManager {


    private Router mRouter;

    public static final String URL_LOGIN_EVENTBUS  = "url_login_eventbus";
    public static final String URL_LOGIN_EVENTBUS2  = "url_login_eventbus2";
    public static final String URL_LOGIN_ASPECT = "url_login_aspect";
    public static final String URL_LOGIN_GENERATE_CODE_FRAGMENT = "url_login_generate_code_fragment";
    public static final String URL_LOGIN_ANDFIX = "url_login_andfix";
    public static final String URL_LOGIN_BUTTER_KNIFE = "url_login_butter_knife";
    public static final String URL_LOGIN_RETROFIT = "url_login_retrofit";
    public static final String URL_LOGIN_THREAD = "url_login_thread";
    public static final String URL_LOGIN_BLUE_TOOTH = "url_login_thread";
    public static final String URL_LOGIN_BLUE_SERVER_TOOTH = "url_login_blue_server_tooth";
    public static final String URL_LOGIN_BLUE_BLE_SERVER_TOOTH = "url_login_blue_ble_server_tooth";
    public static final String URL_LOGIN_BLUE_BLE_CLIENT_TOOTH = "url_login_blue_ble_client_tooth";
    public static final String URL_LOGIN_BLUE_BLE_FASTBLE = "url_login_blue_ble_fastble";
    public static final String URL_LOGIN_OKHTTP = "url_login_okhttp";
    public static final String URL_LOGIN_NIO = "url_login_nio";
    public static final String URL_LOGIN_GLIDE = "url_login_glide";
    public static final String URL_LOGIN_RX = "url_login_rx";
    public static final String URL_LOGIN_BLUE_BLE_FASTBLE_CONFIG_NET = "url_login_blue_ble_fastble_config_net";

    private static AuthRouterManager instance = null;

    public static AuthRouterManager getInstance()
    {
        if(instance == null)
        {
            synchronized (AuthRouterManager.class)
            {
                if(instance == null)
                {
                    instance = new AuthRouterManager();
                }
            }
        }
        return instance;
    }

    private AuthRouterManager()
    {
        init();
    }

    private void init()
    {
        Router.RouterInterceptor interceptor = new Router.RouterInterceptor() {
            @Override
            public boolean interceptor(String url) {
                if(url.contains("login"))
                {

                }
                return false;
            }
        };
        mRouter = new Router();
        mRouter.map(URL_LOGIN_EVENTBUS, EventBusFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_ASPECT, AspectFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_EVENTBUS2, EventBusFragment2.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_GENERATE_CODE_FRAGMENT, GenerateCodeFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_ANDFIX, AndFixFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_BUTTER_KNIFE, ButterKnifeFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_RETROFIT, RetrofitFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_THREAD, ThreadFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_BLUE_TOOTH, TraditionalClientFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_BLUE_SERVER_TOOTH, TraditionalServerFragment.class, CommonFragmentActivity.class);

        mRouter.map(URL_LOGIN_BLUE_BLE_SERVER_TOOTH, BLEServerFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_BLUE_BLE_CLIENT_TOOTH, BLEClientFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_OKHTTP, OkHttpFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_BLUE_BLE_FASTBLE, FastBleFragment.class, CommonFragmentActivity.class);

        mRouter.map(URL_LOGIN_NIO, NioFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_GLIDE, GlideFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_RX, RxbusFragment.class, CommonFragmentActivity.class);
        mRouter.map(URL_LOGIN_BLUE_BLE_FASTBLE_CONFIG_NET, BleConfigNetFragment.class, CommonFragmentActivity.class);
    }

    public void open(Context context,String url)
    {
        mRouter.open(context,url);
    }
}
