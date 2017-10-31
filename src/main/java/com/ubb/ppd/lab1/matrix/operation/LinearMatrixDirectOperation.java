package com.ubb.ppd.lab1.matrix.operation;

import com.ubb.ppd.lab1.Matrix;
import com.ubb.ppd.lab1.Matrix.Dimension;
import com.ubb.ppd.lab1.Util;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class LinearMatrixDirectOperation<T> extends DirectMatrixOperation<T> {
    public LinearMatrixDirectOperation(BinaryOperator<T> basicOperation, Function<Dimension, Matrix<T>> matrixFactory) {
        super(basicOperation, matrixFactory);
    }

    @Override
    public Matrix<T> apply(Matrix<T> m1, Matrix<T> m2) {
        Util.assertCanBeAdded(m1, m2);
        return getMatrixFactory()
                .apply(new Dimension(m1.getLinesCount(), m1.getColumnsCount()))
                .map((line, column) -> getOperator().apply(m1.get(line, column), m2.get(line, column)));
    }
}
