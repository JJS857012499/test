package com.zhidian.test.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 江俊升 on 2019/3/9.
 */
public class MatrixGenerator {


    public static void main(String[] args) {
        double[][] matrix1 = generate(60, 50);
        double[][] matrix2 = generate(50, 70);

        System.out.println("矩阵1");
//        printMatrix(matrix1);
        System.out.println("矩阵2");
//        printMatrix(matrix2);

        double[][] result = new double[60][70];
        long l1 = System.currentTimeMillis();
        multiply(matrix1, matrix2, result);
        long l2 = System.currentTimeMillis();
        System.out.println("串行执行耗时:" + (l2 - l1));
        System.out.println("矩阵1*矩阵2的结果");
//        printMatrix(result);

        long l11 = System.currentTimeMillis();
        multiplyParallel(matrix1, matrix2, result);
        long l22 = System.currentTimeMillis();
        System.out.println("并行执行耗时:" + (l22 - l11));
        System.out.println("矩阵1*矩阵2的结果");
//        printMatrix(result);

    }

    /**
     * 1 2 3
     * 4 5 6
     *
     * @param matrix
     */
    private static void printMatrix(double[][] matrix) {
        int columns = matrix.length;
        for (int i = 0; i < columns; i++) {
            int row = matrix[i].length;
            for (int j = 0; j < row; j++) {
                System.out.print(matrix[i][j] + (j == row - 1 ? "" : ", "));
            }
            System.out.println();
        }
        System.out.println();
    }


    public static double[][] generate(int rows, int columns) {
        double[][] ret = new double[rows][columns];
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                ret[i][j] = random.nextDouble() * 100;
            }
        }
        return ret;
    }


    /**
     * 乘积C的第m行第n列的元素等于矩阵A的第m行的元素与矩阵B的第n列对应元素乘积之和
     *
     * @param matrix1
     * @param matrix2
     * @param result
     */
    public static void multiply(double[][] matrix1, double[][] matrix2, double[][] result) {
        int row1 = matrix1.length;
        int columns1 = matrix1[0].length;

        int columns2 = matrix2[0].length;

        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < columns2; j++) {
                result[i][j] = 0;
                for (int k = 0; k < columns1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
    }


    /**
     * 测试结果，每个元素一个线程，多线程情况反而慢了
     * 如果改为每行一个线程，或者n行一个线程呢？
     * @param matrix1
     * @param matrix2
     * @param result
     */
    public static void multiplyParallel(double[][] matrix1, double[][] matrix2,
                                        double[][] result) {
        List<Thread> threads = new ArrayList<>();
        int rows1 = matrix1.length;
        int columns2 = matrix2[0].length;
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < columns2; j++) {
                IndividualMultiplierTask task = new IndividualMultiplierTask
                        (result, matrix1, matrix2, i, j);
                Thread thread = new Thread(task);
                thread.start();
                threads.add(thread);
                if (threads.size() % 100 == 0) {
                    waitForThreads(threads);
                }
            }
        }
    }

    private static void waitForThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threads.clear();
    }

    public static class IndividualMultiplierTask implements Runnable {
        private final double[][] result;
        private final double[][] matrix1;
        private final double[][] matrix2;
        private final int row;
        private final int column;

        public IndividualMultiplierTask(double[][] result, double[][]
                matrix1, double[][] matrix2,
                                        int i, int j) {
            this.result = result;
            this.matrix1 = matrix1;
            this.matrix2 = matrix2;
            this.row = i;
            this.column = j;
        }

        @Override
        public void run() {
            result[row][column] = 0;
            for (int k = 0; k < matrix1[row].length; k++) {
                result[row][column] += matrix1[row][k] * matrix2[k][column];
            }
        }
    }

}
