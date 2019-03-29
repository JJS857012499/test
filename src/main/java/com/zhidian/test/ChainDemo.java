package com.zhidian.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江俊升 on 2019/3/29.
 */
public class ChainDemo {


    public static void main(String[] args) {
        ExecutorChain executorChain = new DefaultExecutorChain();
        executorChain.addExecutor((chain) -> {
            System.out.println("hello");
            chain.execute();
        });

        executorChain.addExecutor((chain) -> {
            System.out.println("abc");
//            chain.execute();
        });
        executorChain.execute();
    }

    public static class DefaultExecutorChain implements ExecutorChain {
        private List<Executor> executors = new ArrayList<>();

        private int position = 0;

        /**
         * [0]->[1]->[2]
         *
         * @param executor
         */
        @Override
        public void addExecutor(Executor executor) {
            executors.add(executor);
        }

        @Override
        public void execute() {
            Executor executor = executors.get(position);
            position++;
            executor.execute(this);
        }
    }

    interface ExecutorChain {
        void addExecutor(Executor executor);

        void execute();
    }

    @FunctionalInterface
    interface Executor {
        void execute(ExecutorChain chain);
    }

}
