package com.zs.base.router;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-24 下午8:22
 * @description: mytest
 */
public class Router {

    public static final String URL_LOGIN_EVENTBUS  = "url_login_eventbus";
    public static final String URL_LOGIN_EVENTBUS_ = "url_login_eventbus";

    public HashMap<String, RouterItem> routerMap;

    public Router() {
        routerMap = new HashMap<>();
    }


    public void startActivity(Context context, String url) {


    }

    public void map(String url, RouterItem item) {
        routerMap.put(url, item);
    }

    public void open(Context context, String url, Bundle bundle, String bundleKey) {

        RouterItem item = routerMap.get(url);
        Class activity = item.getActivity();
        Class fragment = item.getFragment();

        Intent intent = new Intent();
        intent.setClass(context,activity);

        if(bundle != null)
        {
            intent.putExtra(bundleKey,bundle);
        }
        context.startActivity(intent);
    }


    public static class RouterItem {
        private Class activity;
        private Class fragment;

        public RouterItem(Class activity, Class fragment) {
            this.activity = activity;
            this.fragment = fragment;
        }

        public Class getActivity() {
            return activity;
        }

        public void setActivity(Class activity) {
            this.activity = activity;
        }

        public Class getFragment() {
            return fragment;
        }

        public void setFragment(Class fragment) {
            this.fragment = fragment;
        }
    }
}
