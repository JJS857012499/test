package com.zhidian.test;

import java.io.*;

/**
 * Created by 江俊升 on 2019/2/27.
 */
public class TestSerializable {

    public static void main(String[] args) throws Exception {

        B a = new B();
        a.setA(10);
        a.setB("hello");
        a.setC("world");
        a.setD("test d");
        //创建ObjectOutputStream对象，准备序列化对象s
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("abc"));
        //调用writeObject将对象进行序列化，存储在文件abc中。
        oos.writeObject(a);
        oos.flush();
        oos.close();
        //创建ObjectInputStream对象，准备从文件abc中反序列化SerialClass对象
        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("abc"));
        //调用readObject将存储在文件abc中的字节流反序列化为SerialClass对象。
        a = (B) ois.readObject();
        System.out.println(a.getA());
        System.out.println(a.getB());
        System.out.println(a.getC());
        System.out.println(a.getD());

    }

}

// 由于A没有序列化，序列化该对象则会报NotSerializableException
// 如果是作为父类，子类实现了序列化，则A类的属性是不会被序列化的
class A {
    private Integer a;
    protected String b;
    public String c;

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }
}

class B extends A implements IA {

    private String d;

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }
}

interface IA extends Serializable {

}

