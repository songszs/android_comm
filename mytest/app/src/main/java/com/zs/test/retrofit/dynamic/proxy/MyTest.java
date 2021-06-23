package com.zs.test.retrofit.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-4 下午8:02
 * @description: mytest
 */
public class MyTest {

    public void test() {
        Man man = new Man();

        ISellCar   sellCarProxy   = new SellCarProxy(man);
        ISellHouse sellHouseProxy = new SellHouseProxy(man);

        sellCarProxy.sellCar();
        sellHouseProxy.sellHouse();
    }

    public void testDynamicProxy()
    {
        Man man = new Man();
        SellCarInvocationHandler sellCarInvocationHandler = new SellCarInvocationHandler(man);
        //动态生成字节码Proxy类字节码，在其中调用invoke（）方法
        ISellHouse sellCar = (ISellHouse) Proxy.newProxyInstance(man.getClass().getClassLoader(), man.getClass().getInterfaces(), sellCarInvocationHandler);
        sellCar.sellHouse();
    }


   public class SellCarInvocationHandler implements InvocationHandler
   {

       private Object target;

       public SellCarInvocationHandler(Object target)
       {
           this.target = target;
       }

       @Override
       public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           System.out.println("评估车况，发布到网上，增加报价...");
           Object value = method.invoke(target,args);
           System.out.println("从网上撤销，拿取抽成...");
           return value;
       }
   }

    public class SellHouseInvocationHandler implements InvocationHandler
    {

        private Object target;

        public SellHouseInvocationHandler(Object target)
        {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("发布到网上，增加报价...");
            Object value = method.invoke(target,args);
            System.out.println("从网上撤销，拿取抽成...");
            return value;
        }
    }

}

