package com.ubb.ppd.lab1;

import com.ubb.ppd.lab1.Configuration.TestConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class TestRunner {
    private TestConfig testConfig;
    private MatrixDumper dumper;

    public TestRunner() {
        dumper = MatrixDumper.create();
    }

    @SuppressWarnings("unchecked")
    public TestResult run(TestConfig config) {
        TestResult testResult = new TestResult(config);
        this.testConfig = config;

        try {
            MatrixBuilder builder = getBuilder();
            Matrix matrix1 = builder.withFileContents(config.getFirstFile());
            Matrix matrix2 = builder.withFileContents(config.getFirstFile());
            MatrixOperation matrixOp = getMatrixOperation(builder.getMatrixFactory());
            long startTime = System.currentTimeMillis();
            Matrix result = matrixOp.apply(matrix1, matrix2);
            testResult.setMetric(new TimeMetric(System.currentTimeMillis() - startTime));
            handleComputationResult(result);
        } catch (Exception e) {
            testResult.setException(e);
            e.printStackTrace();
        }
        return testResult;
    }

    private void handleComputationResult(Matrix result) throws FileNotFoundException {
        dumper.dumpToFile(result, String.format("%s%s%s.txt", testConfig.getComputationResultDir(), File.separator, testConfig.getName()));
    }

    @SuppressWarnings("unchecked")
    private MatrixOperation getMatrixOperation(Function<Matrix.Dimension, Matrix> matrixFactory) {
        String runType = testConfig.getRunType();
        String operation = testConfig.getOperation();
        if (Objects.equals(operation, "add")) {
            if (runType.equals("threaded")) {
                return new ThreadedMatrixAddOperation(getAddOperation(), matrixFactory)
                        .setThreadsCount(testConfig.getThreadsCount());
            } else if (runType.equals("linear")) {
                return new LinearMatrixAddOperation(getAddOperation(), matrixFactory);
            }
        }

        if (Objects.equals(operation, "times")) {
            if (runType.equals("threaded")) {
                return new ThreadedMatrixTimesOperation(getAddOperation(), matrixFactory)
                        .setThreadsCount(testConfig.getThreadsCount());
            } else if (runType.equals("linear")) {
                return new LinearMatrixTimesOperation(getAddOperation(), matrixFactory);
            }
        }

        throw invalidConfigException("runType, operations");
    }

    private BiFunction getAddOperation() {
        switch (testConfig.getLoadAs()) {
            case "int":
                return (BiFunction<Integer, Integer, Integer>) (a, b) -> a + b;
            case "double":
                return (BiFunction<Double, Double, Double>) (a, b) -> a + b;
            default:
                throw invalidConfigException("loadAd");
        }

    }

    private BiFunction getTimesOperation() {
        switch (testConfig.getLoadAs()) {
            case "int":
                return (BiFunction<Integer, Integer, Integer>) (a, b) -> a * b;
            case "double":
                return (BiFunction<Double, Double, Double>) (a, b) -> a * b;
            default:
                throw invalidConfigException("loadAd");
        }

    }

    @SuppressWarnings("unchecked")
    private MatrixBuilder getBuilder() {
        switch (testConfig.getLoadAs()) {
            case "int":
                return new IntegerMatrixBuilder(getMatrixFactory());
            case "double":
                return new DoubleMatrixBuilder(getMatrixFactory());
            default:
                throw invalidConfigException("loadAd");
        }
    }

    private RuntimeException invalidConfigException(String key) {
        return new RuntimeException(String.format("Config value for { %s } is invalid.", key));
    }

    private Function getMatrixFactory() {
        if (testConfig.getMatrixType().equals("uni_dimensional_array_backed")) {
            return (Function<Matrix.Dimension, Matrix>) o -> {
                switch (testConfig.getLoadAs()) {
                    case "int":
                        return new UniDimensionalArrayBackedMatrix<Integer>(o);
                    case "double":
                        return new UniDimensionalArrayBackedMatrix<Double>(o);
                    default:
                        throw invalidConfigException("loadAs");
                }
            };
        }

        if (testConfig.getMatrixType().equals("bi_dimensional_array_backed")) {
            return (Function<Matrix.Dimension, Matrix>) o -> {
                switch (testConfig.getLoadAs()) {
                    case "int":
                        return new BiDimensionalArrayBackedMatrix(o);
                    case "double":
                        return new BiDimensionalArrayBackedMatrix(o);
                    default:
                        throw invalidConfigException("loadAd");
                }
            };
        }

        throw invalidConfigException("matrixType");
    }
}
