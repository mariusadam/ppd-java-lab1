package com.ubb.ppd.lab1.matrix.builder;

import com.ubb.ppd.lab1.Matrix;
import com.ubb.ppd.lab1.Util;
import com.ubb.ppd.lab1.matrix.MatrixBuilder;

import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class DoubleMatrixBuilder extends MatrixBuilder<Double> {
    public DoubleMatrixBuilder(Function<Matrix.Dimension, Matrix<Double>> matrixFactory) {
        super(matrixFactory, Double::valueOf);
    }

    @Override
    public Double getRandomValue() {
        return randomGenerator.nextDouble() + randomGenerator.nextInt();
    }

    @Override
    protected Double getZeroValue() {
        return 0D;
    }
}
