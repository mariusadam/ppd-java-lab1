package com.ubb.ppd.lab1.matrix.operation;

import com.ubb.ppd.lab1.Matrix;
import com.ubb.ppd.lab1.matrix.MatrixOperation;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public abstract class DirectMatrixOperation<T> implements MatrixOperation<T> {
    private BinaryOperator<T> operator;
    private Function<Matrix.Dimension, Matrix<T>> matrixFactory;


    public DirectMatrixOperation(BinaryOperator<T> addOperator, Function<Matrix.Dimension, Matrix<T>> matrixFactory) {
        this.operator = addOperator;
        this.matrixFactory = matrixFactory;
    }

    public BiFunction<T, T, T> getOperator() {
        return operator;
    }

    public Function<Matrix.Dimension, Matrix<T>> getMatrixFactory() {
        return matrixFactory;
    }
}
