package com.zhidian.test;

/**
 * 问题一：判断两个单向链表是否相交，如果相交，求出交点
 * 问题二：在一个有环链表中，如何找出链表的入环点？
 * Created by 江俊升 on 2019/3/7.
 */
public class TestRing<E> {

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

    Node head = null;// 头节点
    Node last = null;// 尾节点

    public TestRing() {
        head = last = new Node<>(null);
    }

    public static void main(String[] args) {
        TestRing<Integer> testRing = new TestRing();
        //初始化单链表
        int count = 10;
        for (int i = 1; i <= count; i++) {
            testRing.enqueue(new Node(i));
        }
        //形成环
        testRing.last.next = testRing.head.next.next.next;

        if(testRing.isHaveRing()){
            testRing.findFirstRingNode();
        }
    }

    private void enqueue(Node<E> node) {
        last = last.next = node;
    }

    /**
     * currentNode：doubleSpeedNode
     * 1:2
     * 2:4
     * 3:6
     * 4:8
     * 5:10
     * 6:4
     * 7:6
     * 8:8
     *
     * @return
     */
    private boolean isHaveRing() {
        Node currentNode = head;
        Node doubleSpeedNode = head;
        System.out.println("currentNode：doubleSpeedNode");
        while ((currentNode = currentNode.next) != null
                && (doubleSpeedNode = doubleSpeedNode.next.next) != null) {
            System.out.println(currentNode.getE() + ":" + doubleSpeedNode.getE());
            if (currentNode == doubleSpeedNode) {
                //如果发现相同元素，则表示存在环
                System.out.println("存在环");
                return true;
            }
        }
        return false;
    }


    /**
     * 单向环链表
     *
     *     --- C
     *     \  /
     * A -> B
     *
     * 由快慢指针的启发：他们第一次相遇点C点，假设慢指针走了n步，则快指针走了2n，也就是说慢指针再走n步，则到了C点
     * B点就是我们要求的入环点
     * n = A->B + B->C = C->B + B->C
     * 去掉相同的路径,可以知道 A->B = C->B, 这样，我们可以在相遇的时候，弄两个指针，一个从C点开始，一个从A点开始，如果相遇了，则一定是B点
     */
    private Node findFirstRingNode() {
        Node c = null; // C点

        Node currentNode = head;
        Node doubleSpeedNode = head;
        while ((currentNode = currentNode.next) != null
                && (doubleSpeedNode = doubleSpeedNode.next.next) != null) {
            if (currentNode == doubleSpeedNode) {
                //如果发现相同元素，则表示存在环
                c = currentNode;
                break;
            }
        }

        Node p1,p2;
        p1 = head;
        p2 = c;
        while ((p1=p1.next) != null
               && (p2=p2.next) != null){
            if(p1 == p2){
                System.out.println("链表的入环点：" + p1.getE());
                return p1;
            }
        }
        return null;
    }

}
