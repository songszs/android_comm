package com.zs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-15 上午10:45
 * @description: mytest
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.SOURCE)
public @interface TestParams {
}
