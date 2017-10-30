package com.ubb.ppd.lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class ThreadedMatrixTimesOperation<T> extends DeferredMatrixOperation<T> {
    private Integer threadsCount;

    public ThreadedMatrixTimesOperation(BiFunction<T, T, T> addOperation, Function<Matrix.Dimension, Matrix<T>> matrixFactory) {
        super(addOperation, matrixFactory);
    }

    @Override
    public Matrix<T> apply(Matrix<T> m1, Matrix<T> m2) {
        throw new RuntimeException("No implemented yet");
    }

    public ThreadedMatrixTimesOperation<T> setThreadsCount(Integer threadsCount) {
        this.threadsCount = threadsCount;
        return this;
    }

    class MultiplyTask implements Runnable {
        private Matrix a;
        private Matrix b;
        private Matrix c;
        private int a_i, a_j, b_i, b_j, c_i, c_j, size;
        private static final int MATRIX_SIZE = 1024,
                POOL_SIZE = Runtime.getRuntime().availableProcessors(),
                MINIMUM_THRESHOLD = 4;
        private final ExecutorService exec = Executors.newFixedThreadPool(POOL_SIZE);


        MultiplyTask(Matrix a, Matrix b, Matrix c, int a_i, int a_j, int b_i, int b_j, int c_i, int c_j, int size) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.a_i = a_i;
            this.a_j = a_j;
            this.b_i = b_i;
            this.b_j = b_j;
            this.c_i = c_i;
            this.c_j = c_j;
            this.size = size;
        }

        public void run() {
            System.out.format("[%d,%d]x[%d,%d](%d)\n", a_i, a_j, b_i, b_j, size);
            int h = size / 2;
            if (size <= MINIMUM_THRESHOLD) {
                for (int i = 0; i < size; ++i) {
                    for (int j = 0; j < size; ++j) {
                        for (int k = 0; k < size; ++k) {
                           // c[c_i + i][c_j + j] += a[a_i + i][a_j + k] * b[b_i + k][b_j + j];
                        }
                    }
                }
            } else {
                List<MultiplyTask> tasks = new ArrayList<>();
                Collections.addAll(tasks,
                        new MultiplyTask(a, b, c, a_i, a_j, b_i, b_j, c_i, c_j, h),
                        new MultiplyTask(a, b, c, a_i, a_j + h, b_i + h, b_j, c_i, c_j, h),

                        new MultiplyTask(a, b, c, a_i, a_j, b_i, b_j + h, c_i, c_j + h, h),
                        new MultiplyTask(a, b, c, a_i, a_j + h, b_i + h, b_j + h, c_i, c_j + h, h),

                        new MultiplyTask(a, b, c, a_i + h, a_j, b_i, b_j, c_i + h, c_j, h),
                        new MultiplyTask(a, b, c, a_i + h, a_j + h, b_i + h, b_j, c_i + h, c_j, h),

                        new MultiplyTask(a, b, c, a_i + h, a_j, b_i, b_j + h, c_i + h, c_j + h, h),
                        new MultiplyTask(a, b, c, a_i + h, a_j + h, b_i + h, b_j + h, c_i + h, c_j + h, h)

                );

                Thread[] threads = new Thread[tasks.size() / 2];

                for (int i = 0; i < tasks.size(); i += 2) {
                    threads[i / 2] = new Thread(new Sequentializer(tasks.get(i), tasks.get(i + 1)));
                    exec.execute(threads[i / 2]);
                }
                for (int i = 0; i < threads.length; ++i) {
                    threads[i].run();
                }
                try {
                    for (int i = 0; i < threads.length; ++i) {
                        threads[i].join();
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    class Sequentializer implements Runnable {
        private MultiplyTask first, second;

        Sequentializer(MultiplyTask first, MultiplyTask second) {
            this.first = first;
            this.second = second;
        }

        public void run() {
            first.run();
            second.run();
        }
    }
}
