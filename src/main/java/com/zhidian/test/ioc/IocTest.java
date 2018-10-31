package com.zhidian.test.ioc;

/**
 * Created by 江俊升 on 2018/10/31.
 */
public class IocTest {

    public static void main(String[] args){
        Container container = new SampleContainer();
        container.registerBean(Lol.class);

        container.initWried();

        Lol lol = container.getBean(Lol.class);
        lol.work();
    }
}
