package com.zs.base.router;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zs.base.config.BundleKeys;
import com.zs.base.view.CommonFragmentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-24 下午8:22
 * @description: mytest
 */
public class Router {

    public static final String URL_LOGIN_EVENTBUS  = "url_login_eventbus";
    public static final String URL_LOGIN_EVENTBUS_ = "url_login_eventbus";

    public HashMap<String, RouterItem> mRouterMap;

    public List<RouterInterceptor> mRouterInterceptors;

    public Router() {
        mRouterMap = new HashMap<>();
        mRouterInterceptors = new ArrayList<>();
    }

    public HashMap<String, RouterItem> getRouterMap() {
        return mRouterMap;
    }

    public void map(String url, Class fragment, Class activity) {
        mRouterMap.put(url, new RouterItem(activity, fragment));
    }

    public void map(String url, Class fragment) {
        mRouterMap.put(url, new RouterItem(fragment));
    }

    public void open(Context context, String url) {
        open(context, url, null, null);
    }

    public void addIntercepter(RouterInterceptor interceptor) {
        if (interceptor != null) {
            mRouterInterceptors.add(interceptor);
        }
    }

    public void open(Context context, String url, String bundleKey, Bundle bundle) {

        for (RouterInterceptor interceptor : mRouterInterceptors) {
            if (interceptor.interceptor(url)) {
                return;
            }
        }

        RouterItem item     = mRouterMap.get(url);
        Class      activity = item.getActivity();
        Class      fragment = item.getFragment();

        Intent intent = new Intent();
        intent.setClass(context, activity);
        intent.putExtra(BundleKeys.KEY_FRAGMENT_NAME, fragment.getClass().getName());

        if (bundle != null) {
            intent.putExtra(bundleKey, bundle);
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

        public RouterItem(Class fragment) {
            this.activity = CommonFragmentActivity.class;
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

    public interface RouterInterceptor {
        boolean interceptor(String url);
    }
}
