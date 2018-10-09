package com.zhidian.test.thread;

/**
 * Created by 江俊升 on 2018/9/18.
 */
public class TestException {

    public static void main(String[] args) {

        Runnable task = () -> {
            try {
                a();
            } catch (Exception e) {
                return;
            }
            b();
        };
        new Thread(task).start();
    }

    private static void b() {
        System.out.println("b function");
    }

    private static void a() {
        System.out.println("a function");
        throw new RuntimeException();
    }


}
