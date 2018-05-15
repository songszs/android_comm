package com.zs.test.annotation.generate.method;

import com.zs.annotation.TestMethod;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-15 上午10:47
 * @description: mytest
 */
public interface IAction {

    interface IOpen{
        @TestMethod
        public void open(String state);
    }
}
