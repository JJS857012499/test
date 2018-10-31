package com.zhidian.test.ioc;

import com.zhidian.test.ioc.annotation.Autowired;
import sun.reflect.misc.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 江俊升 on 2018/10/30.
 */
public class SampleContainer implements Container {

    /**
     * 存放所有bean
     */
    private Map<String, Object> beans;

    /**
     * 存放bean跟name的关系
     */
    private Map<String, String> beanKeys;

    public SampleContainer() {
        this.beans = new ConcurrentHashMap<>();
        this.beanKeys = new ConcurrentHashMap<>();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        String className = clazz.getName();
        Object bean = beans.get(className);
        if (bean != null) {
            return (T) bean;
        }
        return null;
    }

    @Override
    public <T> T getBeanByName(String name) {
        String className = beanKeys.get(name);
        Object bean = beans.get(className);
        if (bean != null) {
            return (T) bean;
        }
        return null;
    }

    @Override
    public Object registerBean(Object bean) {
        String name = bean.getClass().getName();
        beans.put(name, bean);
        beanKeys.put(name, name);
        return bean;
    }

    @Override
    public Object registerBean(Class<?> clazz) {
        String className = clazz.getName();
        Object bean = null;
        try {
            bean = ReflectUtil.newInstance(clazz);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        beans.put(className, bean);
        beanKeys.put(className, className);
        return bean;
    }

    @Override
    public Object registerBean(String name, Class<?> clazz) throws IllegalAccessException, InstantiationException {
        String className = clazz.getName();
        Object bean = ReflectUtil.newInstance(clazz);
        beans.put(className, bean);
        beanKeys.put(name, className);
        return bean;
    }

    @Override
    public void remove(Class<?> clazz) {
        String className = clazz.getName();
        beans.remove(className);
        beanKeys.remove(className);
    }

    @Override
    public void removeByName(String name) {
        String className = beanKeys.get(name);
        if (className != null && !className.equals("")) {
            beans.remove(className);
            beanKeys.remove(name);
        }
    }

    @Override
    public Set<String> getBeanNames() {
        return beanKeys.keySet();
    }

    @Override
    public void initWried() {
        Iterator<Object> iterator = beans.values().iterator();
        while (iterator.hasNext()) {
            Object bean = iterator.next();
            inject(bean);
        }

    }

    private void inject(Object bean) {
        try {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                Autowired autowired = field.getAnnotation(Autowired.class);
                if (autowired != null) {
                    Object autowiredField = null;
                    String name = autowired.name();
                    if (name != null && !name.equals("")) {
                        String className = beanKeys.get(name);
                        if (className != null && !className.equals("")) {
                            autowiredField = beans.get(className);
                        }
                        if (autowiredField == null) {
                            throw new RuntimeException("Unable to load " + name);
                        }
                    } else {
                        if (autowired.value() == Class.class) {
                            autowiredField = this.recursiveAssembly(field.getType());
                        } else {
                            //指定类型
                            autowiredField = getBean(autowired.value());
                        }
                        if (autowiredField == null) {
                            autowiredField = recursiveAssembly(autowired.value());
                        }
                    }
                    if (autowiredField == null) {
                        throw new RuntimeException("Unable to load " + field.getType().getCanonicalName());
                    }

                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    field.set(bean, autowiredField);
                    field.setAccessible(accessible);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Object recursiveAssembly(Class<?> clazz) {
        if (null != clazz) {
            try {
                return this.registerBean(clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
