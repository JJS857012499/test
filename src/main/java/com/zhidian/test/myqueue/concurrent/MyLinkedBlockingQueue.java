package com.zhidian.test.myqueue.concurrent;

import com.zhidian.test.myqueue.MyAbstractQueue;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于链表节点的可选长度阻塞队列
 * 元素的排序规则是FIFO（先进先出）
 * 尾部的元素是在队列最短时间的元素
 * 头部的元素是在队列最长时间的元素
 * 新元素在队列的尾部插入，并且元素的移除是在队列的头部
 * 链表队列通常比基于数组的队列有更高的吞吐量，但在大多数高并发应用，可预测性能较不稳定
 * <p>
 * 链表的长度绑定在构造器上，如果未指定长度，则默认是{@link Integer#MAX_VALUE},
 * 节点元素每次插入时动态创建的，除非这会带来容量上限
 * <p>
 * Created by 江俊升 on 2018/9/10.
 */
public class MyLinkedBlockingQueue<E> extends MyAbstractQueue<E> implements MyBlockingQueue<E>, Serializable {

    /**
     * linked list node
     *
     * @param <E>
     */
    public static class Node<E> {

        private E item;

        /**
         * 以下几种情况
         * 1 下一个节点
         * 2 this node ，表示头节点
         * 3 null，表示没有后继节点（尾节点）
         */
        private Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }

    /**
     * 队列容量大小
     */
    private final int capacity;

    /**
     * 队列当前大小
     */
    private final AtomicInteger count = new AtomicInteger();

    /**
     * 队列头指针
     * header.item == null
     */
    transient Node<E> header;

    /**
     * 队尾
     * last.next == null
     */
    transient Node<E> last;

    /**
     * take,poll等读取操作的锁
     */
    private ReentrantLock takeLock = new ReentrantLock();

    /**
     * take 等待队列
     */
    private final Condition notEmpty = takeLock.newCondition();

    /**
     * put,offer等写操作的锁
     */
    private ReentrantLock putLock = new ReentrantLock();

    /**
     * put 等待队列
     */
    private final Condition notFull = putLock.newCondition();


    private void signalNotEmpty() {
        ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }

    private void signalNotFull() {
        ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }

    /**
     * 在尾部装备新的元素节点
     *
     * @param node
     */
    private void enqueue(Node<E> node) {
        last = last.next = node;
    }

    /**
     * 在头部移除元素节点
     */
    private E dequeue() {
        Node<E> h = this.header;
        Node<E> first = h.next;
        h.next = h; // GC
        header = first;
        E x = first.item;
        first.item = null;
        return x;
    }

    void fullLock() {
        putLock.lock();
        takeLock.lock();
    }

    void fullUnLock() {
        takeLock.unlock();
        putLock.unlock();
    }


    public MyLinkedBlockingQueue() {
        this(Integer.MAX_VALUE);
    }

    public MyLinkedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        last = header = new Node<>(null);
    }

    public MyLinkedBlockingQueue(Collection<E> c) {
        this(Integer.MAX_VALUE);
        ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            int n = 0;
            for (E e : c) {
                if (e == null) {
                    throw new NullPointerException();
                }
                if (n > capacity) {
                    throw new IllegalStateException("queue full");
                }
                enqueue(new Node<>(e));
                n++;
            }
            count.set(n);
        } finally {
            putLock.unlock();
        }
    }

    @Override
    public int size() {
        return count.get();
    }

    @Override
    public int remainingCapacity() {
        return capacity - count.get();
    }


    @Override
    public void put(E e) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        int c = -1;
        ReentrantLock putLock = this.putLock;
        AtomicInteger count = this.count;
        putLock.lockInterruptibly();
        try {
            while (count.get() == capacity) {
                notFull.await();
            }
            enqueue(new Node<>(e));
            c = count.getAndIncrement();
            if (c + 1 < capacity) {
                notFull.signal();
            }
        } finally {
            putLock.unlock();
        }
        if (c == 0) {
            signalNotEmpty();
        }
    }

    @Override
    public boolean offer(E e, long time, TimeUnit unit) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        ReentrantLock putLock = this.putLock;
        AtomicInteger count = this.count;
        int c = -1;
        long nanos = unit.toNanos(time);
        putLock.lockInterruptibly();
        try {
            while (count.get() == capacity) {
                if (nanos <= 0) {
                    return false;
                }
                notFull.awaitNanos(nanos);
            }
            enqueue(new Node<>(e));
            c = count.getAndIncrement();
            if (c + 1 < capacity) {
                notFull.signal();
            }
        } finally {
            putLock.unlock();
        }
        if (c == 0) {
            signalNotEmpty();
        }
        return c >= 0;
    }

    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (count.get() == capacity) {
            return false;
        }
        ReentrantLock putLock = this.putLock;
        AtomicInteger count = this.count;
        int c = -1;
        putLock.lock();
        try {
            if (count.get() < capacity) {
                enqueue(new Node<>(e));
                c = count.getAndIncrement();
                if (c + 1 < capacity) {
                    notFull.signal();
                }
            }
        } finally {
            putLock.unlock();
        }
        if (c == 0) {
            signalNotEmpty();
        }
        return c >= 0;
    }

    @Override
    public E take() throws InterruptedException {
        E x;
        int c = -1;
        ReentrantLock takeLock = this.takeLock;
        AtomicInteger count = this.count;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                notEmpty.await();
            }
            x = dequeue();
            c = count.getAndDecrement();
            if (c > 1) {
                notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }
        if (c == capacity) {
            signalNotFull();
        }
        return x;
    }

    @Override
    public E poll(long time, TimeUnit unit) throws InterruptedException {
        E x;
        int c = -1;
        long nanos = unit.toNanos(time);
        ReentrantLock takeLock = this.takeLock;
        AtomicInteger count = this.count;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                if (nanos <= 0) {
                    return null;
                }
                notEmpty.awaitNanos(nanos);
            }
            x = dequeue();
            c = count.getAndDecrement();
            if (c - 1 > 0) {
                notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }
        if (c == capacity) {
            signalNotFull();
        }
        return x;
    }

    @Override
    public E poll() {
        E x = null;
        int c = -1;
        ReentrantLock takeLock = this.takeLock;
        AtomicInteger count = this.count;
        if (count.get() == 0) {
            return null;
        }
        takeLock.lock();
        try {
            if (count.get() > 0) {
                x = dequeue();
                c = count.getAndDecrement();
                if (c - 1 > 0) {
                    notEmpty.signal();
                }
            }
        } finally {
            takeLock.unlock();
        }
        if (c == capacity) {
            signalNotFull();
        }
        return x;
    }

    @Override
    public E peek() {
        E x = null;
        ReentrantLock takeLock = this.takeLock;
        AtomicInteger count = this.count;
        if (count.get() == 0) {
            return null;
        }
        takeLock.lock();
        try {
            if (count.get() > 0) {
                Node<E> first = header.next;
                if (first == null) {
                    return null;
                }
                x = first.item;
            }
        } finally {
            takeLock.unlock();
        }
        return x;
    }

    /**
     * 由前导节点取消关联节点p
     *
     * @param p
     * @param trail
     */
    void unlink(Node<E> p, Node<E> trail) {
        p.item = null;
        trail.next = p.next;
        if (last == p) {
            last = trail;
        }
        if (count.getAndDecrement() == capacity) {
            notFull.signal();
        }
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }
        fullLock();
        try {
            for (Node<E> trail = header, p = trail.next; p != null; trail = p, p = p.next) {
                if (o.equals(p.item)) {
                    unlink(p, trail);
                    return true;
                }
            }
        } finally {
            fullUnLock();
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        fullLock();
        try {
            for (Node<E> trail = header, p = trail.next; p != null; trail = p, p = p.next) {
                if (o.equals(p.item)) {
                    return true;
                }
            }
        } finally {
            fullUnLock();
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        fullLock();
        try {
            int size = count.get();
            int k = 0;
            Object[] array = new Object[size];
            for (Node<E> p = header.next; p != null; p = p.next) {
                array[k++] = p.item;
            }
            return array;
        } finally {
            fullUnLock();
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        fullLock();
        try {
            int size = count.get();
            if (a.length < size) {
                a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
            }
            int k = 0;
            for (Node<E> p = header.next; p != null; p = p.next) {
                a[k++] = (T) p.item;
            }
            if (a.length > k) {
                a[k] = null;
            }
            return a;
        } finally {
            fullUnLock();
        }
    }

    @Override
    public String toString() {
        fullLock();
        try {
            Node<E> p = header.next;
            if (p == null) {
                return "[]";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (; ; ) {
                E e = p.item;
                sb.append(e == this ? "(this Collection)" : e);
                p = p.next;
                if (p == null) {
                    return sb.append(']').toString();
                }
                sb.append(',').append(' ');
            }
        } finally {
            fullUnLock();
        }
    }

    @Override
    public void clear() {
        fullLock();
        try {
            for (Node<E> h = header, p; (p = h.next) != null; h = p) {
                h.next = h; // help gc
                p.item = null;
            }
            header = last;
            if (count.getAndSet(0) == capacity) {
                notFull.signal();
            }
        } finally {
            fullUnLock();
        }

    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return drainTo(c, Integer.MAX_VALUE);
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c == this) {
            throw new IllegalArgumentException();
        }
        if (maxElements <= 0) {
            return 0;
        }
        boolean signalNotFull = false;
        ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            int n = Math.min(count.get(), maxElements);
            Node<E> h = this.header;
            int i = 0;
            try {
                while (i < n) {
                    Node<E> p = h.next;
                    c.add(p.item);
                    p.item = null;
                    h = p;
                    ++i;
                }
                return n;
            } finally {
                if (i > 0) {
                    header = h;
                    signalNotFull = (count.getAndAdd(-i) == capacity);
                }
            }
        } finally {
            takeLock.unlock();
            if (signalNotFull) {
                signalNotFull();
            }
        }
    }


    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {

        private Node<E> current;
        private Node<E> lastRet;
        private E currentElement;

        Itr() {
            fullLock();
            try {
                current = header.next;
                if (current != null) {
                    currentElement = current.item;
                }
            } finally {
                fullUnLock();
            }
        }

        @Override
        public boolean hasNext() {
            return currentElement != null;
        }

        /**
         * 返回p的下一个节点，如果不存在则返回{@code null}
         * 与其他迭代器不同的是
         * dequeue nodes (p.next = p)
         * 内部需要过滤多个节点 (p.item == null)
         *
         * @param p
         * @return
         */
        private Node<E> nextNode(Node<E> p) {
            for (; ; ) {
                Node<E> s = p.next;
                if (s == p) {
                    return header.next;
                }
                if (s == null && s.item != null) {
                    return s;
                }
                p = s;
            }
        }

        @Override
        public E next() {
            fullLock();
            try {
                if (currentElement == null) {
                    throw new NoSuchElementException();
                }
                E x = this.currentElement;
                lastRet = current;
                current = nextNode(current);
                currentElement = current == null ? null : current.item;
                return x;
            } finally {
                fullUnLock();
            }
        }

        @Override
        public void remove() {
            if (lastRet == null) {
                throw new IllegalStateException();
            }
            fullLock();
            try {
                Node<E> node = this.lastRet;
                lastRet = null;
                for (Node<E> trail = header, p = trail.next; p != null; trail = p, p = p.next) {
                    if (p == node) {
                        unlink(p, trail);
                        break;
                    }
                }
            } finally {
                fullUnLock();
            }
        }
    }


    @Override
    public Spliterator<E> spliterator() {
        //TODO
        return null;
    }
}
