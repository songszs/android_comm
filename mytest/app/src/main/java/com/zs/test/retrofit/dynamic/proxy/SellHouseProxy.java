package com.zs.test.retrofit.dynamic.proxy;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-4 下午8:03
 * @description: mytest
 */
public class SellHouseProxy implements ISellHouse {

    private ISellHouse target;

    public SellHouseProxy(ISellHouse target)
    {
        this.target = target;
    }

    @Override
    public void sellHouse() {
        System.out.println("发布到网上，增加报价...");
        target.sellHouse();
        System.out.println("从网上撤销，拿取抽成...");
    }

    public ISellHouse getTarget() {
        return target;
    }

    public void setTarget(ISellHouse target) {
        this.target = target;
    }
}
