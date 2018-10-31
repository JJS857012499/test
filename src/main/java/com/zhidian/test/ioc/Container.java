package com.zhidian.test.ioc;

import java.util.Set;

/**
 * Created by 江俊升 on 2018/10/29.
 */
public interface Container {

    <T> T getBean(Class<T> clazz);

    <T> T getBeanByName(String name);

    Object registerBean(Object bean);

    Object registerBean(Class<?> clazz);

    Object registerBean(String name, Class<?> clazz) throws IllegalAccessException, InstantiationException;

    void remove(Class<?> clazz);

    void removeByName(String name);

    Set<String> getBeanNames();

    void initWried();

}
