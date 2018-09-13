package com.zhidian.test.myqueuetest;

/**
 * 秒杀对象
 * Created by 江俊升 on 2018/9/12.
 */
public class Seckill {

    private Integer i;

    public Seckill(Integer i) {
        this.i = i;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return "Seckill{" +
                "i=" + i +
                '}';
    }
}
