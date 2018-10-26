package com.zhidian.test.counttest;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 江俊升 on 2018/10/23.
 */
public class Driver {

    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(n);
        for (int i = 0; i < n; i++) {
            new Thread(new Worker(startSignal, doneSignal)).start();
        }
        System.out.println("用于触发处于等待状态的线程开始工作......");
        System.out.println("用于触发处于等待状态的线程工作完成，等待状态线程开始工作......");
        startSignal.countDown();
        doneSignal.await();
        System.out.println("Bingo!");
    }

    public static class Worker implements Runnable {
        CountDownLatch startSignal;
        CountDownLatch doneSignal;

        Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await();//等待主线程执行完毕，获得开始执行信号...
                System.out.println("处于等待的线程开始自己预期工作......");
                doneSignal.countDown();//完成预期工作，发出完成信号...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
