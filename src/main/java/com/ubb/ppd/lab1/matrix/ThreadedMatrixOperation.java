package com.ubb.ppd.lab1.matrix;

/**
 * @author Marius Adam
 */
public interface ThreadedMatrixOperation<T> extends MatrixOperation<T> {
    ThreadedMatrixOperation<T> setThreadsCount(Integer count);
}
