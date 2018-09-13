package com.zhidian.test;

import java.math.BigDecimal;

/**
 * Created by 江俊升 on 2018/5/5.
 */
public class TestNull {

    public static void main(String[] args){
        System.out.println((null+"").length());
        System.out.println(new BigDecimal(10.33).toString());
        System.out.println(new BigDecimal("10.33").toString());

    }

}
