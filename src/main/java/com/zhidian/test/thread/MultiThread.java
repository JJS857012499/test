package com.zhidian.test.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by 江俊升 on 2018/9/13.
 */
public class MultiThread {

    /**
     * 现代操作系统在运行一个程序时，会为其创建一个进程。例如，启动一个Java程序，操作 系统就会创建一个Java进程。现代操作系统调度的最小单元是线程，也叫轻量级进程（Light Weight Process），在一个进程里可以创建多个线程，这些线程都拥有各自的计数器、堆栈和局
     * 部变量等属性，并且能够访问共享的内存变量。处理器在这些线程上高速切换，让使用者感觉
     * 到这些线程在同时执行。
     * 一个Java程序从main()方法开始执行，然后按照既定的代码逻辑执行，看似没有其他线程 参与，但实际上Java程序天生就是多线程程序，因为执行main()方法的是一个名称为main的线程。
     *
     * @param args
     */
    public static void main(String[] args) {
        //获取java多线程管理ThreadMXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的Monitor和synchronizer，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        //循环打印
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadName() + ":" + threadInfo.getThreadId() + "]");
        }
    }
}
