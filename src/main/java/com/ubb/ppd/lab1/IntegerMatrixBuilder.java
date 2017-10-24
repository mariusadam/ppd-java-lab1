package com.ubb.ppd.lab1;

import java.security.SecureRandom;
import java.util.Random;
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
        return randomGenerator.nextInt();
    }
}
