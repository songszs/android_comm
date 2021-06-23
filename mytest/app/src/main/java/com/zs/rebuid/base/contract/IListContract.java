package com.zs.rebuid.base.contract;

import android.support.v7.widget.RecyclerView;

import com.zs.rebuid.base.view.base.IBaseView;

import java.util.List;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-03-17 17:49
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public interface IListContract {

    public interface IView extends IBaseView<IPresenter> {

        void setAdapter(RecyclerView.Adapter adapter);


    }

    public interface IPresenter {

        void onItemClick(int index);


    }
}
