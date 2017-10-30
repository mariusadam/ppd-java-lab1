package com.ubb.ppd.lab1;

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
        String path = "/home/marius/IdeaProjects/ppd/lab1/data/test.yml";
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

//        MatrixDumper.create().dumpToFile(new LinearMatrixAddOperation<Integer>(
//                (integer, integer2) -> integer + integer2, UniDimensionalArrayBackedMatrix::new
//        ).apply(matrix, matrix), file);

        ThreadedMatrixAddOperation<Double> transformer = new ThreadedMatrixAddOperation<>(
                (integer, integer2) -> integer + integer2, UniDimensionalArrayBackedMatrix::new
        );

        transformer.setThreadsCount(2);

        MatrixDumper.create().dumpToFile(transformer.apply(matrix, matrix), file);
    }
}
