package com.ubb.ppd.lab1.matrix.builder;

import com.ubb.ppd.lab1.Matrix;
import com.ubb.ppd.lab1.matrix.MatrixBuilder;

import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class IntegerMatrixBuilder extends MatrixBuilder<Integer> {
    public IntegerMatrixBuilder(Function<Matrix.Dimension, Matrix<Integer>> matrixFactory) {
        super(matrixFactory, Integer::valueOf);
    }

    @Override
    public Integer getRandomValue() {
        return randomGenerator.nextInt(10);
    }

    @Override
    protected Integer getZeroValue() {
        return 0;
    }
}
