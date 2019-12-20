package com.zs.design.singleton;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2019-10-24 15:29
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class SingletonTest {


    //如果没用也会初始化  浪费内存
    public static class Singleton2{
        private static Singleton2 INSTANCE = new Singleton2();
        private Singleton2(){}
        public static Singleton2 getInstance(){
            return INSTANCE;
        }
    }

    //单线程使用 多线程线程不安全
    public static class Singleton3{
        private static Singleton3 INSTANCE = null;
        private Singleton3(){}
        public static Singleton3 getInstance(){
            if(INSTANCE == null){
                INSTANCE = new Singleton3();
            }
            return INSTANCE;
        }
    }

    //synchronized方法 只会有一个线程进入 其他线程阻塞
    public static class Singleton4{
        private static Singleton4 INSTANCE = null;
        private Singleton4(){}
        public static synchronized Singleton4 getInstance(){
            if(INSTANCE == null){
                INSTANCE = new Singleton4();
            }
            return INSTANCE;
        }
    }

    //优化了上面的写法，但仍有问题
    //jvm创建一个对象并赋值分为3步
    //1。在堆里面申请内存
    //2。初始化对象
    //3。把内存地址赋值给对象
    //但是这三个步骤23是无序的，也就说有可能对象已经赋值了，但是其实还没有完全初始化，这时候其他线程引用就有可能会报错
    public static class Singleton1{
        private static Singleton1 INSTANCE = null;

        private Singleton1() {

        }

        public static Singleton1 getInstance() {
            if (INSTANCE == null) {
                synchronized (Singleton1.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new Singleton1();
                    }
                }
            }
            return INSTANCE;
        }
    }

    /**
     * volatile能够保证赋值的原子性
     * 解决了上面写法的问题
     */
    public static class Singleton5{
        private static volatile Singleton5 INSTANCE = null;
        private Singleton5(){}
        public static Singleton5 getInstance(){
            if(INSTANCE == null){
                synchronized (Singleton5.class){
                    if (INSTANCE == null) {
                        INSTANCE = new Singleton5();
                    }
                }
            }
            return INSTANCE;
        }
    }

    /**
     * 这种写法没有问题
     * 静态内部类与外部类操作分开
     * 一开始外部类会先加载 内部类不会加载
     * 当调用方法的时候静态内部类才会加载
     */
    public static class Singleton6{
        private Singleton6(){}

        private static class SingletonHolder{
            private static Singleton6 INSTANCE = new Singleton6();
        }

        public static Singleton6 getInstance(){
            return SingletonHolder.INSTANCE;
        }
    }


    /**
     * 这种写法简单高效 推荐写法
     */
    public enum Singleton7{
        INSTANCE;
        public void fun(){}
    }

}
