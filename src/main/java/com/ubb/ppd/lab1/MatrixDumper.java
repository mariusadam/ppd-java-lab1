package com.ubb.ppd.lab1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author Marius Adam
 */
public class MatrixDumper {
    private MatrixDumper() {
    }

    public static MatrixDumper create() {
        return new MatrixDumper();
    }

    public String dumpToString(Matrix matrix) {
        return dumpToString(matrix, System.lineSeparator(), " ");
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

    public void dumpToFile(Matrix matrix, String filePath) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(filePath)) {
            out.print(dumpToString(matrix));
        }
    }
}
