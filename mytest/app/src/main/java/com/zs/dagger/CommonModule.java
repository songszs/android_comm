package com.zs.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2019-10-30 09:39
 * @email: gnoszsong@gmail.com
 * @description: 用于提供注射的对象
 */
@Module
public class CommonModule {

    private ICommonView iView;

    public CommonModule(ICommonView iView) {
        this.iView = iView;
    }

    @Provides
    @ActivityScope
    public ICommonView provideICommonView(){
        return iView;
    }

}
