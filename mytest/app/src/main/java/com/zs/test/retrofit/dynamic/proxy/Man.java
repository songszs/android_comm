package com.zs.test.retrofit.dynamic.proxy;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-5 上午10:09
 * @description: mytest
 */
public class Man implements ISellCar,ISellHouse {
    @Override
    public void sellCar() {
        System.out.println("我要卖车");
    }

    @Override
    public void sellHouse() {
        System.out.println("我要卖房");
    }
}
