package com.zhidian.test.myqueue.concurrent;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.zhidian.test.myqueue.MyQueue;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by 江俊升 on 2018/9/8.
 */
public interface MyBlockingQueue<E> extends MyQueue<E> {

    /**
     * 插入指定元素到队列，如果队列空间可用，则立马返回true,否则抛出IllegalStateException
     *
     * @param e
     * @return
     */
    @Override
    boolean add(E e);

    /**
     * 插入指定元素到队列，如果队列空间可用，则立马返回true,否则返回false
     *
     * @param e
     * @return
     */
    @Override
    boolean offer(E e);


    /**
     * 插入指定元素到队列，如果空间不可用，则等待
     *
     * @return
     */
    void put(E e) throws InterruptedException;

    /**
     * 插入指定元素到队列，如果队列空间不用，则等待指定时间直至队列空间可用
     *
     * @param e    待插入的元素
     * @param time 等待时间，单位{@code unit}
     * @param unit 等待时间单位
     * @return 成功返回{@code true}, 失败返回{@code false}
     * @throws InterruptedException 如果等待被中断，则抛出{@code InterruptedException}
     */
    boolean offer(E e, long time, @NotNull TimeUnit unit) throws InterruptedException;

    /**
     * 检查并移除队列头部元素，如果队列为空，则等待到有元素可用
     *
     * @return
     * @throws InterruptedException
     */
    @NotNull
    E take() throws InterruptedException;

    /**
     * 检索并移除队列头部元素，如果队列为空，则等待指定的时间
     *
     * @param time 等待时间，单位{@code unit}
     * @param unit 等待时间单位
     * @return 返回队列头部元素，如果在等待时间内，队列没有可用元素，则返回{@code null}
     * @throws InterruptedException
     */
    @Nullable
    E poll(long time, TimeUnit unit) throws InterruptedException;

    /**
     * 队列空间大小
     * @return
     */
    @Override
    int size();

    /**
     * 队列可用空间
     * @return
     */
    int remainingCapacity();

    /**
     * 移除队列一个元素{@code e}, {@code o.equals(e)}.
     *
     * @param o
     * @return {@code true} 如果有元素被移除
     */
    @Override
    boolean remove(Object o);

    /**
     * {@code true} 如果队列包含有指定的元素。
     * 更确切的说，队列至少有一个元素{@code e}，{@code e.equals(o)}
     *
     * @param o
     * @return
     */
    @Override
    boolean contains(Object o);

    /**
     * 把队列里所有可用元素移除,并添加到指定的集合
     *
     * @param c
     * @return
     */
    int drainTo(Collection<? super E> c);


    /**
     * 把队列里所有可用元素移除最多指定的数量，并将移除的元素添加到指定的集合中
     * @param c
     * @param maxElements
     * @return
     */
    int drainTo(Collection<? super E> c, int maxElements);
}
