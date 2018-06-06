package com.zs.test.retrofit.dynamic.proxy;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-4 下午8:03
 * @description: mytest
 */
public class SellCarProxy implements ISellCar {

    private ISellCar target;

    public SellCarProxy(ISellCar target)
    {
        this.target = target;
    }

    @Override
    public void sellCar() {
        System.out.println("评估车况，发布到网上，增加报价...");
        target.sellCar();
        System.out.println("从网上撤销，拿取抽成...");
    }

    public ISellCar getTarget() {
        return target;
    }

    public void setTarget(ISellCar target) {
        this.target = target;
    }
}
