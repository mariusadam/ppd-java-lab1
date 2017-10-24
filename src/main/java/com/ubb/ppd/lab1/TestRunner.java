package com.ubb.ppd.lab1;

import com.ubb.ppd.lab1.Configuration.TestConfig;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Marius Adam
 */
public class TestRunner {
    private TestConfig testConfig;

    @SuppressWarnings("unchecked")
    public TestResult run(TestConfig config) {
        TestResult testResult = new TestResult(config);
        this.testConfig = config;

        try {
            MatrixBuilder builder = getBuilder();
            Matrix matrix1 = builder.withFileContents(config.getFirstFile());
            Matrix matrix2 = builder.withFileContents(config.getFirstFile());
            MatrixTransformer transformer = getTransformer(builder.getMatrixFactory());
            long startTime = System.currentTimeMillis();
            Matrix result = transformer.apply(matrix1, matrix2);
            testResult.setMetric(new TimeMetric(System.currentTimeMillis() - startTime));
            handleComputationResult(result);
        } catch (Exception e) {
            testResult.setException(e);
            e.printStackTrace();
        }
        return testResult;
    }

    private void handleComputationResult(Matrix result) {

    }

    @SuppressWarnings("unchecked")
    private MatrixTransformer getTransformer(Function<Matrix.Dimension, Matrix> matrixFactory) {
        String runType = testConfig.getRunType();
        String operation = testConfig.getOperation();
        if (Objects.equals(operation, "add")) {
            if (runType.equals("threaded")) {
                return new MatrixThreadedTransformer(getAtomicOperation(), matrixFactory)
                        .setThreadsCount(testConfig.getThreadsCount());
            } else if (runType.equals("linear")) {
                return new MatrixLinearDirectTransformer(getAtomicOperation(), matrixFactory);
            }
        }

        throw invalidConfigException("runType, operations");
    }

    private BiFunction getAtomicOperation() {
        switch (testConfig.getLoadAs()) {
            case "int":
                return (BiFunction<Integer, Integer, Integer>) (a, b) -> a + b;
            case "double":
                return (BiFunction<Double, Double, Double>) (a, b) -> a + b;
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
                        throw invalidConfigException("loadAd");
                }
            };
        }

        throw invalidConfigException("matrixType");
    }
}
