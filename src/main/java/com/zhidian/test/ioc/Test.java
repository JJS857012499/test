package com.zhidian.test.ioc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 江俊升 on 2018/10/29.
 */
public class Test {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        Class<?> clazz = loader.loadClass("com.zhidian.test.ioc.Hero");
//        Constructor<?> cons = clazz.getConstructor();
//        Hero hero = (Hero) cons.newInstance();
//        Method setName = clazz.getMethod("setName", String.class);
//        setName.invoke(hero, "小鱼人");
//        Method setOutfit = clazz.getMethod("setOutfit", String.class);
//        setOutfit.invoke(hero, "烈爆魔杖");
//        hero.say();

        Class<?> clazz = Class.forName("com.zhidian.test.ioc.Hero");
        Hero hero = (Hero) clazz.newInstance();
        Method setName = clazz.getMethod("setName", String.class);
        setName.invoke(hero, "小鱼人");
        Method setOutfit = clazz.getMethod("setOutfit", String.class);
        setOutfit.invoke(hero, "烈爆魔杖");
        hero.say();
    }

}
