package com.zhidian.test;

/**
 * Created by 江俊升 on 2018/10/13.
 */
public class IntegerTest {

    public static void main(String[] args) {
        //totalPage参数非常大，超过 int 的最大值时，i递增到int的最大值后，i++ 会发生翻转，变成一个负数，从而使 for 会进入死循环
        long totalPage = new Long(Integer.MAX_VALUE) + 1;
        for (int i = 0; i < totalPage; i++) {
            if (i < 0) {
                System.out.println(i);
            }
        }
    }

}
