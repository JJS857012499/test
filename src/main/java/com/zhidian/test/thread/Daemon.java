package com.zhidian.test.thread;

/**
 * Created by 江俊升 on 2018/1/31.
 */
public class Daemon {

    /**
     * main线程（非Daemon线程）在启动了线程DaemonRunner之后随着main方法执行完毕而终止，而此时Java虚拟
     * 机中已经没有非Daemon线程，虚拟机需要退出。Java虚拟机中的所有Daemon线程都需要立即
     * 终止，因此DaemonRunner立即终止，但是DaemonRunner中的finally块并没有执行。
     * <p>
     * setDaemon方法有如下注释
     * The Java Virtual Machine exits when the only
     * threads running are all daemon threads.
     *
     * @param args
     */
    public static void main(String... args) {
        Thread thread = new Thread(new DaemonRunner(), "Daemon thread");
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(100);
                System.out.print("run here...");
            } finally {
                System.out.print("run finally here...");
            }
        }
    }

}
