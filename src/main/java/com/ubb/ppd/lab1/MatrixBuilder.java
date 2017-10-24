package com.ubb.ppd.lab1;

import com.ubb.ppd.lab1.Matrix.Dimension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Marius Adam
 */
@SuppressWarnings("unchecked")
public abstract class MatrixBuilder<T> {
    protected static Random randomGenerator = new SecureRandom();

    private Function<Dimension, Matrix<T>> matrixFactory;

    private Function<String, T> elementConverter;

    public MatrixBuilder(Function<Dimension, Matrix<T>> matrixFactory, Function<String, T> elementConverter) {
        this.matrixFactory = matrixFactory;
        this.elementConverter = elementConverter;
    }

    public Function<Dimension, Matrix<T>> getMatrixFactory() {
        return matrixFactory;
    }

    public Matrix<T> withRandomValues(Integer lines, Integer cols) {
        return matrixFactory
                .apply(new Dimension(lines, cols))
                .map((line, column) -> getRandomValue());
    }

    public Matrix<T> withGivenValue(Integer lines, Integer cols, T value) {
        return matrixFactory.apply(new Dimension(lines, cols)).map((line, column) -> value);
    }

    public Matrix<T> withFileContents(String filePath) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            return new MatrixLoader<>(matrixFactory, elementConverter).load(stream);
        }
    }

    abstract public T getRandomValue();
}