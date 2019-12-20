package com.zs.dagger;

import dagger.Component;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2019-10-30 09:42
 * @email: gnoszsong@gmail.com
 * @description: 相当于注射器
 */
@ActivityScope
@Component(modules = CommonModule.class)
public interface CommonComponent {
    void inject(DaggerFragment fragment);

}
