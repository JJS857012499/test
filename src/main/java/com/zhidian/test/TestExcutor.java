package com.zhidian.test;

import java.util.concurrent.*;

/**
 * Created by 江俊升 on 2019/3/29.
 */
public class TestExcutor {


    public static void main(String[] args) {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, r -> {
            Thread thread = new Thread(r);
            thread.setName("my-test");
            return thread;
        });

        Callable task1 = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello");
            return "hello";
        };

        long l1 = System.currentTimeMillis();

        ScheduledFuture<String> future1 = executor.schedule(task1, 2000, TimeUnit.MILLISECONDS);
        ScheduledFuture<String> future2 = executor.schedule(task1, 2, TimeUnit.SECONDS);
        ScheduledFuture<String> future3 = executor.schedule(task1, 2, TimeUnit.SECONDS);

        //这里是比较当前时间，还有多长才执行任务
        //第一个任务，等了2秒，执行一秒，接着执行2,3任务，各需要1秒，所以一共花费5秒
        System.out.println("delay:" + future1.getDelay(TimeUnit.MILLISECONDS));
        System.out.println("delay:" + future2.getDelay(TimeUnit.SECONDS));
        System.out.println("delay:" + future3.getDelay(TimeUnit.SECONDS));

        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (!executor.isTerminated()) {
                executor.shutdownNow();
                System.out.println("executor shutdown now!!!");
            }
        }
        long l2 = System.currentTimeMillis();
        System.out.println("一共花费：" + TimeUnit.SECONDS.convert(l2 - l1, TimeUnit.MILLISECONDS));
    }
}
