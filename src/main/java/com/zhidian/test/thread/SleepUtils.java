package com.zhidian.test.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by 江俊升 on 2018/1/31.
 */
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}
