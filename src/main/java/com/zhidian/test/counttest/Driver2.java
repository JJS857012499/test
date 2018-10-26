package com.zhidian.test.counttest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 江俊升 on 2018/10/26.
 */
public class Driver2 {


    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
        int count = 10;
        final CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            threadPool.execute(new MyRunnable(latch, i));
        }

        latch.await();
        System.err.println("等待线程被唤醒！");
        threadPool.shutdown();
    }

    public static class MyRunnable implements Runnable{
        private CountDownLatch latch;
        private Integer i;

        public MyRunnable(CountDownLatch latch, int i) {
            this.latch = latch;
            this.i = i;
        }


        @Override
        public void run() {
            System.out.println("线程 " + i + " 执行完成....");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        }
    }

}
