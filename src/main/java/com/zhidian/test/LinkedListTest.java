package com.zhidian.test;

/**
 * Created by 江俊升 on 2018/10/9.
 */
public class LinkedListTest<E> {


    static class Node<E> {
        E e;
        /**
         * 以下几种情况
         * 1 下一个节点
         * 2 this node ，表示头节点
         * 3 null，表示没有后继节点（尾节点）
         */
        Node next;

        public Node(E e) {
            this.e = e;
        }

        public E getE() {
            return e;
        }

        public void setE(E e) {
            this.e = e;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    static Node head = null;// 头节点
    static Node last = null;// 尾节点

    static {
        head = last = new Node<>(null);
    }


    public static void main(String[] args) {
        LinkedListTest<Integer> linkedList = new LinkedListTest<>();
        int count =  10; // 0、1、10
        for (int i = 0; i < count; i++) {
            linkedList.enqueue(new Node<>(i+1));
        }
        System.out.println("reverse前：" + linkedList.toString());
        linkedList.reverse();
        System.out.println("reverse后：" + linkedList.toString());
    }

    public void reverse() {
        Node<E> p, q;
        p = head.next;
        q = null;
        for (; ; ) {
            if (p == head || p == null) {
                break;
            }
            Node<E> bak = p.next; //bak
            p.next = q;
            q = p;
            p = bak;
        }
        last = head.next;
        head.next = q;
    }

    private void enqueue(Node<E> node) {
        last = last.next = node;
    }

    @Override
    public String toString() {
        Node p = head.next;
        if (p == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (; ; ) {
            sb.append(p.e);
            p = p.next;
            if (p == null)
                return sb.append("]").toString();
            sb.append(",");
        }
    }

}
