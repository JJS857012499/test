package com.zhidian.test.clazz;

import java.lang.reflect.Method;

/**
 * Created by 江俊升 on 2019/6/11.
 */
public class Test {

    public static void target(int i) {
        new Exception("#" + i).printStackTrace();
    }

    /**
     * 动态实现和本地实现相比，其运行效率要快上 20 倍 [2] 。这是因为动态实现无需经过 Java 到 C++ 再到 Java 的切换，但由于生成字节码十分耗时，仅调用一次的话，反而是本地实现要快上 3 到 4 倍 [3]。
     *
     * 考虑到许多反射调用仅会执行一次，Java 虚拟机设置了一个阈值 15（可以通过 -Dsun.reflect.inflationThreshold= 来调整），当某个反射调用的调用次数在 15 之下时，采用本地实现；当达到 15 时，便开始动态生成字节码，并将委派实现的委派对象切换至动态实现，这个过程我们称之为 Inflation。
     *
     * java -verbose:class Test
     * 可以看到，在第 15 次（从 0 开始数）反射调用时，我们便触发了动态实现的生成。这时候，Java 虚拟机额外加载了不少类。其中，最重要的当属 GeneratedMethodAccessor1。并且，从第 16 次反射调用开始，我们便切换至这个刚刚生成的动态实现。
     *
     * 反射调用的 Inflation 机制是可以通过参数（-Dsun.reflect.noInflation=true）来关闭的。这样一来，在反射调用一开始便会直接生成动态实现，而不会使用委派实现本地实现。
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("com.zhidian.test.clazz.Test");
        Method method = clazz.getMethod("target", int.class);
        for (int i = 0; i < 20; i++) {
            method.invoke(null, i);
        }
    }

}
