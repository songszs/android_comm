package com.zs.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2019-10-30 09:38
 * @email: gnoszsong@gmail.com
 * @description: description
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
