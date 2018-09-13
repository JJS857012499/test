package com.zhidian.test.myqueuetest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 江俊升 on 2018/9/12.
 */
public class TestMyblockingQueue {

    static int corePool = Runtime.getRuntime().availableProcessors();

    static ThreadPoolExecutor productExecutor = new ThreadPoolExecutor(corePool, corePool + 1, 120, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000));

    static ThreadPoolExecutor consumeExecutor = new ThreadPoolExecutor(corePool, corePool + 1, 120, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000));


    public static void main(String[] args) {
        // 模拟一万个请求
        for (int i = 0; i < 10000; i++) {
            ProductTask productTask = new ProductTask(new Seckill(i));
            productExecutor.execute(productTask);
        }

        //模拟一百个消费者
        for (int i = 0; i < 100; i++) {
            ConsumeTask consumeTask = new ConsumeTask();
            consumeExecutor.execute(consumeTask);
        }
    }

    static class ProductTask implements Runnable {

        private Seckill seckill;

        public ProductTask(Seckill seckill) {
            this.seckill = seckill;
        }

        @Override
        public void run() {
            try {
                SeckillQueue.getInstance().product(seckill);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ConsumeTask implements Runnable {

        @Override
        public void run() {
            for (; ; ) {
                Seckill seckill = null;
                try {
                    seckill = (Seckill) SeckillQueue.getInstance().consume();
//                    System.out.println("当前处理 seckill：" + seckill);
                    System.out.println("当前队列还剩余：" + SeckillQueue.getInstance().size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
