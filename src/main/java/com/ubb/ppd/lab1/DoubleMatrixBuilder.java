package com.ubb.ppd.lab1;

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
        return randomGenerator.nextDouble();
    }
}
