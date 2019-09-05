package com.zs.test.eventbus;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-4 下午4:32
 * @description: mytest
 */
public class CustomEvent {

    private String message;
    public CustomEvent(String message)
    {
        this.message = message;
    }

    public CustomEvent(int num)
    {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
