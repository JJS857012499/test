package com.zhidian.test.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by 江俊升 on 2019/4/17.
 */
public class TestForkJoinPool {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        CountTask task = new CountTask(1, 80);
        pool.invoke(task);
        try {
            Integer result = task.get();
            System.out.println("运算结果：" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    /**
     * 加法运算
     * [1,4]
     * 1+2+3+4
     */
    public static class CountTask extends RecursiveTask<Integer> {
        /** 如果任务足够小就可以运算 */
        private static final int THREAD_HOLD = 3;
        /** 开始和结束值*/
        private int start, end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            boolean canProcess = end - start <= THREAD_HOLD;
            if (canProcess) {
                //如果任务足够小，执行运算功能
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                //否则拆分任务（对半拆分任务）
                int middle = (end + start) / 2;
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle + 1, end);
                leftTask.fork();
                rightTask.fork();
                sum = leftTask.join() + rightTask.join();
            }
            return sum;
        }
    }


}
