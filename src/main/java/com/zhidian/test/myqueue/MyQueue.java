package com.zhidian.test.myqueue;

import java.util.Collection;

/**
 * Created by 江俊升 on 2018/9/8.
 */
public interface MyQueue<E> extends Collection<E> {


    /**
     * 添加指定元素到队列，如果有队列空间可用则立即返回true,否则抛出IllegalStateException
     *
     * @param e
     * @return
     */
    @Override
    boolean add(E e);

    /**
     * 添加指定元素到队列，如果不受空间容量限制立即返回true，否则立即返回false。 非阻塞
     *
     * @param e
     * @return
     */
    boolean offer(E e);

    /**
     * 检索并移除队列的头部元素。如果队列为空，则抛出异常
     *
     * @return
     */
    E remove();

    /**
     * 检索并移除队列头部元素。如果队列为空，则返回{@code null}
     *
     * @return
     */
    E poll();

    /**
     * 检索并不移除队列头部元素。如果队列为空，则抛出异常
     * @return
     */
    E element();

    /**
     * 检索并不移除队列头部元素。如果队列为空，则返回{@code null}
     * @return
     */
    E peek();

}
