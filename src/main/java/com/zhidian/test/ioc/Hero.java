package com.zhidian.test.ioc;

/**
 * Created by 江俊升 on 2018/10/29.
 */
public class Hero {

    private String name;

    private String outfit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutfit() {
        return outfit;
    }

    public void setOutfit(String outfit) {
        this.outfit = outfit;
    }

    public void say() {
        System.out.println(name + "购买了" + outfit);
    }
}
