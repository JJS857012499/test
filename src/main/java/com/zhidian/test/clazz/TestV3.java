package com.zhidian.test.clazz;

import java.lang.reflect.Method;

/**
 * Created by 江俊升 on 2019/6/11.
 */
public class TestV3 {


    public static void target(int i) {
        // 空方法
    }

    public static void main(String[] args) throws Exception {

        Class<?> klass = Class.forName("com.zhidian.test.clazz.TestV3");
        Method method = klass.getMethod("target", int.class);

        //原本的反射调用被内联了，从而使得即时编译器中的逃逸分析将原本新建的 Object 数组判定为不逃逸的对象。
        //
        //如果一个对象不逃逸，那么即时编译器可以选择栈分配甚至是虚拟分配，也就是不占用堆空间
        //如果在循环外新建数组，即时编译器无法确定这个数组会不会中途被更改，因此无法优化掉访问数组的操作，可谓是得不偿失。

        Object[] arg = new Object[1]; // 在循环外构造参数数组
        arg[0] = 128;

        long current = System.currentTimeMillis();
        for (int i = 1; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }

            method.invoke(null, arg);
        }
    }

}
