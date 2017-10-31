package com.ubb.ppd.lab1;

/**
 * @author Marius Adam
 */
public class Util {
    public static <T> void assertCanBeAdded(Matrix<T> m1, Matrix<T> m2) {
        if (!m1.canBeAdded(m2)) {
            throw new RuntimeException("Matrices must have the same dimension to add them.");
        }
    }

    public static void assertNotNull(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }

    public static double scaleRandomDouble(double val) {
        return -10000 + val * (10000 + 10000);
    }
}
