package com.zhidian.test.thread;

/**
 * Created by 江俊升 on 2018/2/2.
 */
public class Join {

    public static void main(String[] args) {

        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Domino(previous), i + "");
            t.start();
            previous = t;
        }
        SleepUtils.second(5);
        System.out.println("terminal main : ");
    }


    static class Domino implements Runnable {

        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("terminal id : " + Thread.currentThread()
                    .getName());
        }
    }
}
