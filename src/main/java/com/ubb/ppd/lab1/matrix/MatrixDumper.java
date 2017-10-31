package com.ubb.ppd.lab1.matrix;

import com.ubb.ppd.lab1.Matrix;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class MatrixDumper {
    private Function<Object, String> stringConverter;
    private MatrixDumper(Function<Object, String> stringConverter) {
        this.stringConverter = stringConverter;
    }

    public static MatrixDumper create() {
        return new MatrixDumper(Object::toString);
    }

    public static MatrixDumper create(Function<Object, String> stringConverter) {
        return new MatrixDumper(stringConverter);
    }

    public String dumpToString(Matrix matrix) {
        return dumpToString(matrix, System.lineSeparator(), MatrixLoader.VALUES_SEP);
    }

    public String dumpToString(Matrix matrix, String linesSep, String valuesSep) {
        StringBuilder sb = new StringBuilder();

        sb
                .append(matrix.getLinesCount())
                .append(valuesSep)
                .append(matrix.getColumnsCount())
                .append(linesSep);

        for (int i = 0; i < matrix.getLinesCount(); i++) {
            for (int j = 0; j < matrix.getColumnsCount(); j++) {
                sb.append(matrix.get(i, j));
                if (j < matrix.getColumnsCount() - 1) {
                    sb.append(valuesSep);
                }
            }
            if (i < matrix.getLinesCount() - 1) {
                sb.append(linesSep);
            }
        }

        return sb.toString();
    }

    public void dumpToFile(Matrix matrix, String filePath) {
        try (PrintWriter out = new PrintWriter(filePath)) {
            out.print(dumpToString(matrix));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
