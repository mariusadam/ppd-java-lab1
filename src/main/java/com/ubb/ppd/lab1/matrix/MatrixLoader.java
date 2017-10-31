package com.ubb.ppd.lab1.matrix;

import com.ubb.ppd.lab1.Matrix;
import com.ubb.ppd.lab1.Matrix.Dimension;
import sun.misc.Regexp;

import java.util.Iterator;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author Marius Adam
 */
public class MatrixLoader<T> {
    public static final String VALUES_SEP = " | ";
    private Function<Dimension, Matrix<T>> matrixFactory;
    private Function<String, T> itemConverter;

    public MatrixLoader(Function<Dimension, Matrix<T>> matrixFactory, Function<String, T> itemConverter) {
        this.matrixFactory = matrixFactory;
        this.itemConverter = itemConverter;
    }

    public Matrix<T> load(Stream<String> lines) {
        return load(lines, Pattern.quote(VALUES_SEP));
    }

    public Matrix<T> load(Stream<String> lines, String valuesSep) {
        Iterator<String> linesIterator = lines.iterator();
        if (!linesIterator.hasNext()) {
            throw new RuntimeException("Missing matrix dimensions");
        }

        String[] dimensions = linesIterator.next().split(valuesSep);
        if (dimensions.length != 2) {
            throw new RuntimeException("First line must contain the number of lines and columns");
        }

        Integer linesCount = Integer.valueOf(dimensions[0]);
        Integer columnsCount = Integer.valueOf(dimensions[1]);

        int i = 0;

        Matrix<T> matrix = matrixFactory.apply(new Dimension(linesCount, columnsCount));
        while (linesIterator.hasNext()) {
            String[] row = linesIterator.next().split(valuesSep);
            if (row.length != columnsCount) {
                throw new RuntimeException(String.format("Invalid values count on row %d .", i));
            }
            for (int j = 0; j < row.length; j++) {
                matrix.set(i, j, itemConverter.apply(row[j]));
            }
            i++;
        }

        if (i != linesCount) {
            throw new RuntimeException("Lines count do not match the actual lines in the file.");
        }

        return matrix;
    }
}
