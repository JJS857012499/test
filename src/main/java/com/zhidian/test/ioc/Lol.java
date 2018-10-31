package com.zhidian.test.ioc;

import com.zhidian.test.ioc.annotation.Autowired;

/**
 * Created by 江俊升 on 2018/10/31.
 */
public class Lol {

    @Autowired
    private FaceService faceService;

    public void work() {
        faceService.buy("剑圣", 5);
    }

}
