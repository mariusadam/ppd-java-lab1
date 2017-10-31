package com.ubb.ppd.lab1.matrix.operation;

import com.ubb.ppd.lab1.Matrix;
import com.ubb.ppd.lab1.matrix.MatrixBuilder;
import com.ubb.ppd.lab1.matrix.MatrixOperation;

import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class LinearMatrixTimesOperation<T> implements MatrixOperation<T> {
    private BinaryOperator<T> addOperator;
    private BinaryOperator<T> multiplyOperator;
    private MatrixBuilder<T> matrixBuilder;

    public LinearMatrixTimesOperation(BinaryOperator<T> addOperation, BinaryOperator<T> multiplyOperator, MatrixBuilder<T> matrixBuilder) {
        this.addOperator = addOperation;
        this.multiplyOperator = multiplyOperator;
        this.matrixBuilder = matrixBuilder;
    }
    @Override
    public Matrix<T> apply(Matrix<T> m1, Matrix<T> m2) {
        Matrix<T> result = matrixBuilder.withZeroValue(m1.getLinesCount(), m2.getColumnsCount());
        for (int i = 0; i < m1.getLinesCount(); i++) { // aRow
            for (int j = 0; j < m2.getColumnsCount(); j++) { // bColumn
                for (int k = 0; k < result.getColumnsCount(); k++) { // aColumn
                    result.set(
                            i,
                            j,
                            addOperator.apply(
                                    result.get(i, j),
                                    multiplyOperator.apply(
                                            m1.get(i, k),
                                            m2.get(k, j)
                                    )
                            )
                    );
                }
            }
        }

        return result;
    }
}
