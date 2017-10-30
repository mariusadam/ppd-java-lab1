package com.ubb.ppd.lab1;

import com.ubb.ppd.lab1.Matrix.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class ThreadedMatrixAddOperation<T> extends DeferredMatrixOperation<T> {
    private Integer threadsCount;
    private Matrix<T> m1;
    private Matrix<T> m2;
    private Matrix<T> result;

    public ThreadedMatrixAddOperation(BiFunction<T, T, T> basicOperation, Function<Dimension, Matrix<T>> matrixFactory) {
        super(basicOperation, matrixFactory);
    }

    @Override
    public Matrix<T> apply(Matrix<T> m1, Matrix<T> m2) {
        Util.assertCanBeAdded(m1, m2);
        Util.assertNotNull(threadsCount);

        Matrix<T> result = getMatrixFactory().apply(new Dimension(m1.getLinesCount(), m2.getColumnsCount()));
        setContext(m1, m2, result);
        int size = m1.getLinesCount() * m1.getColumnsCount();
        int span = size / threadsCount;
        int remaining = size % threadsCount;
        List<Thread> threads = new ArrayList<>();
        int start = 0;
        int stop = 0;

        for (int i = 0; i < threadsCount; i++) {
            stop = stop + span;
            if (remaining > 0) {
                stop = stop + 1;
                remaining = remaining - 1;
            }

            threads.add(new Thread(getJob(start, stop)));
            threads.get(i).start();
            start = stop;
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        setContext(null, null, null);
        return result;
    }

    private void setContext(Matrix<T> m1, Matrix<T> m2, Matrix<T> result) {
        this.m1 = m1;
        this.m2 = m2;
        this.result = result;
    }

    public Runnable getJob(Integer start, Integer stop) {
        return () -> {
            for (int i = start; i < stop; i++) {
                result.set(
                        i,
                        getDeferredOp().apply(m1.get(i), m2.get(i))
                );
            }
        };
    }

    public ThreadedMatrixAddOperation setThreadsCount(Integer threadsCount) {
        this.threadsCount = threadsCount;
        return this;
    }
}
