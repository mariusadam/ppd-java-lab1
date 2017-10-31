package com.ubb.ppd.lab1.matrix;

import com.ubb.ppd.lab1.Matrix;

/**
 * @author Marius Adam
 */
public interface MatrixOperation<T> {
    /**
     * Translates the two matrices in a third one
     * @param m1 The first matrix
     * @param m2 The second matrix
     * @return The result matrix
     */
    Matrix<T> apply(Matrix<T> m1, Matrix<T> m2);
}
