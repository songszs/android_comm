package com.edaoxiu.aspectjtest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-3-14 下午4:10
 * @description: mytest
 */
@Aspect
public class FirstTest {

    private static final String TAG = FirstTest.class.getSimpleName();

    @Before("execution (* com.zs.mytest.MainActivity.onCreate(..))")
    public void adviceOnCreate(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Toast.makeText((Context) joinPoint.getTarget(), "aspectj" + signature.toShortString(), Toast.LENGTH_SHORT).show();
        Log.e(TAG, "" + joinPoint.getTarget());
    }
}
