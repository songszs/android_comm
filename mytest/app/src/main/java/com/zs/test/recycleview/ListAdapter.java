package com.zs.test.recycleview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-21 21:08
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class ListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }
}
