package com.ubb.ppd.lab1;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public abstract class MatrixDirectTransformer<T> implements MatrixTransformer<T> {
    private BiFunction<T, T, T> basicOperation;
    private Function<Matrix.Dimension, Matrix<T>> matrixFactory;


    public MatrixDirectTransformer(BiFunction<T, T, T> basicOperation, Function<Matrix.Dimension, Matrix<T>> matrixFactory) {
        this.basicOperation = basicOperation;
        this.matrixFactory = matrixFactory;
    }

    public BiFunction<T, T, T> getBasicOperation() {
        return basicOperation;
    }

    public Function<Matrix.Dimension, Matrix<T>> getMatrixFactory() {
        return matrixFactory;
    }
}
