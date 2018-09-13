package com.zhidian.test.myqueuetest;

import com.zhidian.test.myqueue.concurrent.MyBlockingQueue;
import com.zhidian.test.myqueue.concurrent.MyLinkedBlockingQueue;

/**
 * 秒杀队列
 * tip：
 * 队列是为了做缓冲作用，防止高并发请求冲垮下面的应用（压垮数据库等等）
 * 基于jvm的队列,
 * 重启时候要阻止消息的入队，并且保证消息都被消费。
 * 异常退出，断电等如何防止消息丢失。
 * <p>
 * Created by 江俊升 on 2018/9/12.
 */
public class SeckillQueue<T> {

    /**
     * 这里设置比较小，是为了测试多线程操作MyLinkedBockingQueue
     */
    static final int MAX_QUEUE_SIZE = 100;

    private MyBlockingQueue<T> queue = new MyLinkedBlockingQueue<>(MAX_QUEUE_SIZE);

//    private BlockingQueue<T> queue = new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);

    private SeckillQueue() {
    }

    public static class SingleHold {
        private static SeckillQueue instance = new SeckillQueue();
    }

    public static SeckillQueue getInstance() {
        return SingleHold.instance;
    }

    public void product(T seckill) throws InterruptedException {
        queue.put(seckill);
    }

    public T consume() throws InterruptedException {
        return queue.take();
    }

    public int size() {
        return queue.size();
    }
}
