package com.zhidian.test;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程 a，b  a打印0 b打印1，然后打印100个，每1秒打印10个
 */
public class ZeroAndOneThread {
    private static AtomicInteger count = new AtomicInteger(1);
    private static AtomicInteger countNum = new AtomicInteger(0);

    static class AThread implements Runnable{

        @Override
        public void run() {
            while ((count.getAndIncrement()<=100)){
                System.out.print(0);
                if(count.get()%10==0){
                    try {
                        Thread.currentThread().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
    static class BThread implements Runnable{

        @Override
        public void run() {
            while ((count.getAndIncrement()<=100)){
                System.out.print(1);
                if(count.get()%10==0){
                    try {
                        Thread.currentThread().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            final Thread t1 = new Thread(new AThread());
            final Thread t2 = new Thread(new BThread());
            boolean blf = true;
            @Override
            public void run() {
                if(blf){
                    t1.start();
                    t2.start();
                    blf = false;
                }else {
                    t1.notify();
                    t2.notify();
                }
            }
        },1000);
    }

}