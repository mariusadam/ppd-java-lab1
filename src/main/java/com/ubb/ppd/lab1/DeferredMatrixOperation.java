package com.ubb.ppd.lab1;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public abstract class DeferredMatrixOperation<T> implements MatrixOperation<T> {
    private BiFunction<T, T, T> deferredOp;
    private Function<Matrix.Dimension, Matrix<T>> matrixFactory;


    public DeferredMatrixOperation(BiFunction<T, T, T> deferredOp, Function<Matrix.Dimension, Matrix<T>> matrixFactory) {
        this.deferredOp = deferredOp;
        this.matrixFactory = matrixFactory;
    }

    public BiFunction<T, T, T> getDeferredOp() {
        return deferredOp;
    }

    public Function<Matrix.Dimension, Matrix<T>> getMatrixFactory() {
        return matrixFactory;
    }
}
