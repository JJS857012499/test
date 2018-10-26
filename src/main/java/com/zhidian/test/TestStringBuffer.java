package com.zhidian.test;

/**
 * Created by 江俊升 on 2018/10/22.
 */
public class TestStringBuffer {

    public static void main(String[] args){
        StringBuilder sb = new StringBuilder(10);
        sb.append("1234");
        System.out.println(sb.length());
        System.out.println(sb.capacity());
    }
}
