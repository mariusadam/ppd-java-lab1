package com.ubb.ppd.lab1;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class LinearMatrixTimesOperation<T> extends DeferredMatrixOperation<T> {
    public LinearMatrixTimesOperation(BiFunction<T, T, T> addOperation, Function<Matrix.Dimension, Matrix<T>> matrixFactory) {
        super(addOperation, matrixFactory);
    }

    @Override
    public Matrix<T> apply(Matrix<T> m1, Matrix<T> m2) {
        Matrix<T> result = getMatrixFactory().apply(new Matrix.Dimension(m1.getLinesCount(), m2.getColumnsCount()));
        for (int i = 0; i < m1.getLinesCount(); i++) { // aRow
            for (int j = 0; j < m2.getColumnsCount(); j++) { // bColumn
                for (int k = 0; k < result.getColumnsCount(); k++) { // aColumn
                    result.set(
                            i,
                            j,
                            getDeferredOp().apply(
                                    m1.get(i, k),
                                    m2.get(k, j)
                            )
                    );
                }
            }
        }

        return result;
    }
}
