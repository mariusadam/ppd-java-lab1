package com.ubb.ppd.lab1.matrix.operation;

import com.ubb.ppd.lab1.Matrix;
import com.ubb.ppd.lab1.Matrix.Dimension;
import com.ubb.ppd.lab1.Util;
import com.ubb.ppd.lab1.matrix.ThreadedMatrixOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * @author Marius Adam
 */
public class ThreadedDirectMatrixOperation<T> extends DirectMatrixOperation<T> implements ThreadedMatrixOperation<T>{
    private static Logger logger = Logger.getLogger(ThreadedMatrixOperation.class.getName());
    private Integer threadsCount;
    private Matrix<T> m1;
    private Matrix<T> m2;
    private Matrix<T> result;

    public ThreadedDirectMatrixOperation(BinaryOperator<T> basicOperation, Function<Dimension, Matrix<T>> matrixFactory) {
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

//        logger.info("Creating " + threadsCount + " threads");
        for (int i = 0; i < threadsCount; i++) {
            stop = stop + span;
            if (remaining > 0) {
                stop = stop + 1;
                remaining = remaining - 1;
            }

//            logger.info("Thread " + Integer.toString(i + 1) + " works " + Integer.toString(stop - start));
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
                        getOperator().apply(m1.get(i), m2.get(i))
                );
            }
        };
    }

    public ThreadedDirectMatrixOperation<T> setThreadsCount(Integer threadsCount) {
        this.threadsCount = threadsCount;
        return this;
    }
}
