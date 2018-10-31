package com.zhidian.test.ioc;

/**
 * Created by 江俊升 on 2018/10/31.
 */
public class TestClassLoader {


    /**
     * 类装载器工作机制
     * 类装载器就是寻找类的节码文件并构造出类在JVM内部表示对象的组件。在Java中，类装载器把一个类装入JVM中，要经过以下步骤：
     *
     * [1.]装载：查找和导入Class文件；
     * [2.]链接：执行校验、准备和解析步骤，其中解析步骤是可以选择的：
     * [2.1]校验：检查载入Class文件数据的正确性；
     * [2.2]准备：给类的静态变量分配存储空间；
     * [2.3]解析：将符号引用转成直接引用；
     * [3.]初始化：对类的静态变量、静态代码块执行初始化工作。
     *
     * 类装载工作由ClassLoader及其子类负责，ClassLoader是一个重要的Java运行时系统组件，它负责在运行时查找和装入Class字节码文件。JVM在运行时会产生三个ClassLoader：根装载器、ExtClassLoader（扩展类装载器）和AppClassLoader（系统类装载器）。其中，根装载器不是ClassLoader的子类，它使用C++编写，因此我们在Java中看不到它，根装载器负责装载JRE的核心类库，如JRE目标下的rt.jar、charsets.jar等。ExtClassLoader和AppClassLoader都是ClassLoader的子类。其中ExtClassLoader负责装载JRE扩展目录ext中的JAR类包；AppClassLoader负责装载Classpath路径下的类包。
     *
     * 这三个类装载器之间存在父子层级关系，即根装载器是ExtClassLoader的父装载器，ExtClassLoader是AppClassLoader的父装载器。默认情况下，使用AppClassLoader装载应用程序的类，我们可以做一个实验：
     * @param args
     */
    public static void main(String[] args){
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            System.out.println("current loader" + classLoader);
            System.out.println("parent loader" + classLoader.getParent());
            System.out.println("granderparent loader" + classLoader.getParent().getParent());
    }
    /**
     * 通过以上的输出信息，我们知道当前的ClassLoader是AppClassLoader，父ClassLoader是ExtClassLoader，祖父ClassLoader是根类装载器，因为在Java中无法获得它的句柄，所以仅返回null。
     */

}
