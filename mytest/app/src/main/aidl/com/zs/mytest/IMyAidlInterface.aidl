// IMyAidlInterface.aidl
package com.zs.mytest;
import com.zs.mode.Info;
import com.zs.test.aidl.OnCallInfo;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void printSomething(String content);

    String getFromClient();

    void addCallback(inout OnCallInfo callback);


}
