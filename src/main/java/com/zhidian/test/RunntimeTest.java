package com.zhidian.test;

/**
 * @Author jiangjunsheng
 * @Date 2019/11/21
 */
public class RunntimeTest {


    /**
     * 给虚拟机注册一个钩子方法，当收到killer信号时候，会回调该方法
     * @param args
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> System.out.println("shutdown hook..."))
        );
    }

}
