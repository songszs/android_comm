package com.zs.base.router;

import android.content.Context;

import com.zs.base.view.CommonFragmentActivity;
import com.zs.test.andfix.AndFixFragment;
import com.zs.test.annotation.generate.GenerateCodeFragment;
import com.zs.test.aspect.AspectFragment;
import com.zs.test.butterknife.ButterKnifeFragment;
import com.zs.test.eventbus.EventBusFragment;
import com.zs.test.eventbus.EventBusFragment2;
import com.zs.test.retrofit.RetrofitFragment;

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
    }

    public void open(Context context,String url)
    {
        mRouter.open(context,url);
    }
}
