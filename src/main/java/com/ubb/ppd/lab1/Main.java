package com.ubb.ppd.lab1;

import com.ubb.ppd.lab1.framework.*;
import com.ubb.ppd.lab1.matrix.MatrixDumper;
import com.ubb.ppd.lab1.matrix.UniDimensionalArrayBackedMatrix;
import com.ubb.ppd.lab1.matrix.builder.ComplexMatrixBuilder;
import com.ubb.ppd.lab1.matrix.builder.DoubleMatrixBuilder;
import com.ubb.ppd.lab1.matrix.builder.IntegerMatrixBuilder;
import com.ubb.ppd.lab1.matrix.operation.ThreadedDirectMatrixOperation;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marius Adam
 */
public class Main {
    public static void main(String... args) throws IOException {
        generateData();
        String path = "/home/marius/IdeaProjects/ppd/lab1/data/test-times.yml";
        Yaml yaml = new Yaml();
        try( InputStream in = Files.newInputStream(Paths.get(path)) ) {
            Configuration config = yaml.loadAs( in, Configuration.class );
            // System.out.println( config.toString() );
            List<TestResult> results = new ArrayList<>();
            TestRunner runner = new TestRunner();
            config.getTestConfigs().forEach(config1 -> results.add(runner.run(config1)));
            TestsResultsExporter exporter = new CsvTestsResultsExporter(new File(config.getBenchmarksFile()));
            exporter.export(results);
        }
    }

    public static void test() throws FileNotFoundException {
        double value = 1.3;
        Matrix<Double> matrix = new DoubleMatrixBuilder(UniDimensionalArrayBackedMatrix::new).withGivenValue(10, 10, value);
        String file = "data/matrix-test-"+ value  +".txt";

//        MatrixDumper.create().dumpToFile(new LinearMatrixDirectOperation<Integer>(
//                (integer, integer2) -> integer + integer2, UniDimensionalArrayBackedMatrix::new
//        ).apply(matrix, matrix), file);

        ThreadedDirectMatrixOperation<Double> transformer = new ThreadedDirectMatrixOperation<>(
                (integer, integer2) -> integer + integer2, UniDimensionalArrayBackedMatrix::new
        );

        transformer.setThreadsCount(2);

        MatrixDumper.create().dumpToFile(transformer.apply(matrix, matrix), file);
    }


    public static void generateData() {
        String dirPath = "data/input/";
//        generateDoubleMatrix(dirPath, 10, 10);
//        generateDoubleMatrix(dirPath, 10, 10);
//        generateDoubleMatrix(dirPath, 100, 100);
//        generateDoubleMatrix(dirPath, 100, 100);
//        generateDoubleMatrix(dirPath, 1000, 1000);
//        generateDoubleMatrix(dirPath, 1000, 1000);

//        generateComplexMatrix(dirPath, 10, 10);
//        generateComplexMatrix(dirPath, 10, 10);
//        generateComplexMatrix(dirPath, 100, 100);
//        generateComplexMatrix(dirPath, 100, 100);
//        generateComplexMatrix(dirPath, 1000, 1000);
//        generateComplexMatrix(dirPath, 1000, 1000);

//        generateIntegerMatrix(dirPath, 10, 10);
//        generateIntegerMatrix(dirPath, 10, 10);
//        generateIntegerMatrix(dirPath, 100, 100);
//        generateIntegerMatrix(dirPath, 100, 100);
//        generateIntegerMatrix(dirPath, 1000, 1000);
//        generateIntegerMatrix(dirPath, 1000, 1000);
    }

    public static void generateDoubleMatrix(String dirPath, int lines, int cols) {
        MatrixDumper.create().dumpToFile(
                new DoubleMatrixBuilder(UniDimensionalArrayBackedMatrix::new).withRandomValues(lines, cols),
                getFileName(dirPath, "double", lines, cols)
        );
    }

    public static void generateComplexMatrix(String filePrefix, int lines, int cols) {
        MatrixDumper.create().dumpToFile(
                new ComplexMatrixBuilder(UniDimensionalArrayBackedMatrix::new).withRandomValues(lines, cols),
                getFileName(filePrefix, "complex", lines, cols)
        );
    }

    public static void generateIntegerMatrix(String filePrefix, int lines, int cols) {
        MatrixDumper.create().dumpToFile(
                new IntegerMatrixBuilder(UniDimensionalArrayBackedMatrix::new).withRandomValues(lines, cols),
                getFileName(filePrefix, "int", lines, cols)
        );
    }
    public static String getFileName(String prefix, String type, int lines, int cols) {
        return getFileName(prefix, type, lines, cols, 1);
    }

    public static String getFileName(String dir, String type, int lines, int cols, int key) {
        String file =  String.format("%s%s%s-%dX%d_%d.mat", dir, File.separator, type, lines, cols, key);
        File f = new File(file);
        if (f.exists()) {
            return getFileName(dir, type, lines, cols, key + 1);
        }

        return file;
    }
}
