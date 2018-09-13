package com.zhidian.test;

import java.util.HashMap;

/**
 * Created by 江俊升 on 2018/4/10.
 */
public class HashMapMultiThread {

    static HashMap<String, String> al = new HashMap<>(2 >> 4);

    public static class AddThread implements Runnable {

        private int start = 0;

        public AddThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < 100000; i += 2) {
                al.put(Integer.toString(i), Integer.toBinaryString(i));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread(0));
        Thread t2 = new Thread(new AddThread(1));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(al.size());
    }


}
