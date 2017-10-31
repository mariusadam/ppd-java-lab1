package com.ubb.ppd.lab1.matrix.builder;

import com.ubb.ppd.lab1.Matrix;
import com.ubb.ppd.lab1.Util;
import com.ubb.ppd.lab1.matrix.MatrixBuilder;
import com.ubb.ppd.lab1.type.ComplexNumber;
import org.apache.commons.math3.complex.Complex;

import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class ComplexMatrixBuilder extends MatrixBuilder<Complex>{
    public ComplexMatrixBuilder(Function<Matrix.Dimension, Matrix<Complex>> matrixFactory) {
        super(matrixFactory, ComplexNumber::parse);
    }

    @Override
    public Complex getRandomValue() {
        return new ComplexNumber(
                randomGenerator.nextDouble() + randomGenerator.nextInt(),
                randomGenerator.nextDouble() + randomGenerator.nextInt()
        );
    }


    @Override
    protected Complex getZeroValue() {
        return Complex.ZERO;
    }
}
