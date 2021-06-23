package com.zs.test.ref;

import java.util.HashMap;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2020-02-23 22:07
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public class HashMapRef {

    private HashMap<RefK,RefV> hashMap = new HashMap<>();

    public void testRefkGc(){
        RefK key = new RefK();
        RefV value = new RefV();
        hashMap.put(key,value);
    }
}
