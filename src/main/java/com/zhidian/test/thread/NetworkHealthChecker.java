package com.zhidian.test.thread;

import com.zhidian.test.thread.count.BaseHealthChecker;

import java.util.concurrent.CountDownLatch;

/**
 * 这个类继承了BaseHealthChecker，实现了verifyService()方法。DatabaseHealthChecker.java和CacheHealthChecker.java除了服务名和休眠时间外，与NetworkHealthChecker.java是一样的。
 * Created by 江俊升 on 2018/2/7.
 */
public class NetworkHealthChecker extends BaseHealthChecker {
    public NetworkHealthChecker(CountDownLatch latch) {
        super("Network Service", latch);
    }

    @Override
    public void verifyService() {
        System.out.println("Checking " + this.getServiceName());
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}