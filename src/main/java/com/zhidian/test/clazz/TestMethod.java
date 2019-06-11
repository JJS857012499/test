package com.zhidian.test.clazz;

import java.lang.reflect.Method;

/**
 * Created by 江俊升 on 2019/6/11.
 */
public class TestMethod {

    public static void target(int i) {

    }

    //反射获取的方法是方法区的一份拷贝，放在对内存，
    //会优先遍历当前类 public 方法，找不到找 父类（class or interface）
    public static void main(String[] args) throws Exception {
        Method target1 = TestMethod.class.getMethod("target", int.class);
        Method target2 = TestMethod.class.getMethod("target", int.class);
        System.out.println(target1.equals(target2));
        System.out.println(target1 ==target2);

    }

}
