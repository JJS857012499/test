package com.zhidian.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 江俊升 on 2019/4/1.
 */
public class TestTmp {

    private Integer i = 0;

    private static Integer j = 0;

    private static final AtomicInteger k = new AtomicInteger(0);
    private static AtomicInteger l = new AtomicInteger(0);

    private volatile static AtomicInteger h = new AtomicInteger(0);

    private void test01(int count) {
        long l1 = System.currentTimeMillis();
        Integer i = this.i;
        for (int c = 0; c < count; c++) {
            i++;
        }
        long l2 = System.currentTimeMillis();
        System.out.println("局部变量，一共花费：" + (l2 - l1));
    }

    private void test02(int count) {
        long l1 = System.currentTimeMillis();
        for (int c = 0; c < count; c++) {
            i++;
        }
        long l2 = System.currentTimeMillis();
        System.out.println("成员变量，一共花费：" + (l2 - l1));
    }

    private void test03(int count) {
        long l1 = System.currentTimeMillis();
        for (int c = 0; c < count; c++) {
            j++;
        }
        long l2 = System.currentTimeMillis();
        System.out.println("静态成员变量，一共花费：" + (l2 - l1));
    }

    private void test04_1(int count) {
        long l1 = System.currentTimeMillis();
        for (int c = 0; c < count; c++) {
            k.getAndIncrement();
        }
        long l2 = System.currentTimeMillis();
        System.out.println("静态final成员变量，一共花费：" + (l2 - l1));
    }

    private void test04_2(int count) {
        long l1 = System.currentTimeMillis();
        for (int c = 0; c < count; c++) {
           l.getAndIncrement();
        }
        long l2 = System.currentTimeMillis();
        System.out.println("静态成员变量，一共花费：" + (l2 - l1));
    }

    private void test05(int count) {
        long l1 = System.currentTimeMillis();
        for (int c = 0; c < count; c++) {
            h.getAndIncrement();
        }
        long l2 = System.currentTimeMillis();
        System.out.println("volatile静态成员变量，一共花费：" + (l2 - l1));
    }

    public static void main(String[] args) {
        new TestTmp().test01(1000000000);
        new TestTmp().test02(1000000000);
        new TestTmp().test03(1000000000);
        new TestTmp().test04_1(1000000000);
        new TestTmp().test04_2(1000000000);
        new TestTmp().test05(1000000000);
    }


}
