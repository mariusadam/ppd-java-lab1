package com.ubb.ppd.lab1.matrix.operation;

import com.ubb.ppd.lab1.Matrix;
import com.ubb.ppd.lab1.matrix.MatrixBuilder;
import com.ubb.ppd.lab1.matrix.MatrixOperation;
import com.ubb.ppd.lab1.matrix.ThreadedMatrixOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class ThreadedMatrixTimesOperation<T> implements MatrixOperation<T>, ThreadedMatrixOperation<T> {
    private BinaryOperator<T> addOperator;
    private BinaryOperator<T> multiplyOperator;
    private Integer threadsCount;
    private MatrixBuilder<T> matrixBuilder;

    public ThreadedMatrixTimesOperation(BinaryOperator<T> addOperation, BinaryOperator<T> multiplyOperator, MatrixBuilder<T> matrixBuilder) {
        this.addOperator = addOperation;
        this.multiplyOperator = multiplyOperator;
        this.matrixBuilder = matrixBuilder;
    }

    @Override
    public Matrix<T> apply(Matrix<T> firstMatrix, Matrix<T> secondMatrix) {
        int numberOfThreads = Math.min(threadsCount, firstMatrix.getLinesCount());
        int step = firstMatrix.getLinesCount() / numberOfThreads;
        int rest = firstMatrix.getColumnsCount() % numberOfThreads;
        int startIndex = 0;
        int endIndex = startIndex + step;
        // here we'll store the sum result matrix
        Matrix<T> result = matrixBuilder.withZeroValue(firstMatrix.getLinesCount(), secondMatrix.getColumnsCount());
        List<Thread> threads = new ArrayList<>();

        for (int tpos = 0; tpos < numberOfThreads; tpos++) {
            /*
              When the matrixSize is not divisible with the numberOfThreads
              we'll distribute more elements to the thread.
             */
            if (rest > 0) {
                rest--;
                endIndex++;
            }
            int finalEndIndex = endIndex;
            int finalStartIndex = startIndex;
            Thread thread = new Thread(() -> {
                for (int i = finalStartIndex; i < finalEndIndex; i++) {
                    for (int j = 0; j < secondMatrix.getColumnsCount(); j++) {
                        for (int k = 0; k < result.getColumnsCount(); k++) {
                            result.set(
                                    i,
                                    j,
                                    addOperator.apply(
                                            result.get(i, j),
                                            multiplyOperator.apply(
                                                    firstMatrix.get(i, k),
                                                    secondMatrix.get(k, j)
                                            )
                                    )
                            );
                        }
                    }
                }
            });
            threads.add(thread);
            thread.start();
            startIndex = endIndex;
            endIndex = startIndex + step;
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return result;
    }

    public ThreadedMatrixTimesOperation<T> setThreadsCount(Integer threadsCount) {
        this.threadsCount = threadsCount;
        return this;
    }
}
