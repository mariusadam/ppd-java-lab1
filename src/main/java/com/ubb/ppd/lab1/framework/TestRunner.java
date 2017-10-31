package com.ubb.ppd.lab1.framework;

import com.ubb.ppd.lab1.Matrix;
import com.ubb.ppd.lab1.matrix.*;
import com.ubb.ppd.lab1.matrix.builder.ComplexMatrixBuilder;
import com.ubb.ppd.lab1.matrix.builder.DoubleMatrixBuilder;
import com.ubb.ppd.lab1.matrix.builder.IntegerMatrixBuilder;
import com.ubb.ppd.lab1.matrix.operation.LinearMatrixDirectOperation;
import com.ubb.ppd.lab1.matrix.operation.LinearMatrixTimesOperation;
import com.ubb.ppd.lab1.matrix.operation.ThreadedDirectMatrixOperation;
import com.ubb.ppd.lab1.matrix.operation.ThreadedMatrixTimesOperation;
import com.ubb.ppd.lab1.type.ComplexNumber;
import org.apache.commons.math3.complex.Complex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * @author Marius Adam
 */
public class TestRunner {
    private static Logger logger = Logger.getLogger(TestRunner.class.getName());
    private Configuration.TestConfig testConfig;
    private MatrixDumper dumper;

    public TestRunner() {
        dumper = MatrixDumper.create();
    }

    @SuppressWarnings("unchecked")
    public TestResult run(Configuration.TestConfig config) {
        TestResult testResult = new TestResult(config);
        this.testConfig = config;

        try {
            logger.info("Loading matrices for test " + config.getName());
            MatrixBuilder builder = getBuilder();
            Matrix matrix1 = builder.withFileContents(config.getFirstFile());
            Matrix matrix2 = builder.withFileContents(config.getSecondFile());
            logger.info("Loaded matrices for test " + config.getName());

            MatrixOperation matrixOp = getMatrixOperation(builder);
            logger.info("Applying operations for test " + config.getName());
            long startTime = System.currentTimeMillis();
            Matrix result = matrixOp.apply(matrix1, matrix2);
            testResult.setMetric(new TimeMetric(System.currentTimeMillis() - startTime));

            logger.info("Writing result to file for " + config.getName());
            handleComputationResult(result);
        } catch (Exception e) {
            testResult.setException(e);
            e.printStackTrace();
        }
        return testResult;
    }

    private void handleComputationResult(Matrix result) throws FileNotFoundException {
        dumper.dumpToFile(result, String.format("%s%s%s.mat", testConfig.getComputationResultDir(), File.separator, testConfig.getName()));
    }

    @SuppressWarnings("unchecked")
    private MatrixOperation getMatrixOperation(MatrixBuilder builder) {
        String runType = testConfig.getRunType();
        String operation = testConfig.getOperation();
        if (Objects.equals(operation, "add")) {
            if (runType.equals("threaded")) {
                return new ThreadedDirectMatrixOperation(getAddOperator(), builder.getMatrixFactory())
                        .setThreadsCount(testConfig.getThreadsCount());
            } else if (runType.equals("linear")) {
                return new LinearMatrixDirectOperation(getAddOperator(), builder.getMatrixFactory());
            }
        }

        if (Objects.equals(operation, "custom")) {
            if (runType.equals("threaded")) {
                return new ThreadedDirectMatrixOperation(getCustomOperator(), builder.getMatrixFactory())
                        .setThreadsCount(testConfig.getThreadsCount());
            } else if (runType.equals("linear")) {
                return new LinearMatrixDirectOperation(getCustomOperator(), builder.getMatrixFactory());
            }
        }

        if (Objects.equals(operation, "times")) {
            if (runType.equals("threaded")) {
                return new ThreadedMatrixTimesOperation(getAddOperator(), getTimesOperator(), builder)
                        .setThreadsCount(testConfig.getThreadsCount());
            } else if (runType.equals("linear")) {
                return new LinearMatrixTimesOperation(getAddOperator(), getTimesOperator(), builder);
            }
        }

        throw invalidConfigException("runType, operations");
    }

    private BinaryOperator getAddOperator() {
        switch (testConfig.getLoadAs()) {
            case "int":
                return (BinaryOperator<Integer>) (a, b) -> a + b;
            case "double":
                return (BinaryOperator<Double>) (a, b) -> a + b;
            case "complex":
                return (BinaryOperator<Complex>) Complex::add;
            default:
                throw invalidConfigException("loadAd");
        }

    }

    private BinaryOperator getCustomOperator() {
        switch (testConfig.getLoadAs()) {
            case "double":
                return (BinaryOperator<Double>) (a, b) -> 1 / (1 / a + 1 / b);
            case "complex":
                Complex one = new ComplexNumber(1);
                return (BinaryOperator<Complex>) (a, b) -> one.divide(
                        one.divide(a).add(one.divide(b))
                );
            default:
                throw invalidConfigException("loadAd");
        }

    }

    private BinaryOperator getTimesOperator() {
        switch (testConfig.getLoadAs()) {
            case "int":
                return (BinaryOperator<Integer>) (a, b) -> a * b;
            case "double":
                return (BinaryOperator<Double>) (a, b) -> a * b;
            case "complex":
                return (BinaryOperator<Complex>) Complex::multiply;
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
            case "complex":
                return new ComplexMatrixBuilder(getMatrixFactory());
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
                    case "complex":
                        return new BiDimensionalArrayBackedMatrix<Complex>(o);
                    default:
                        throw invalidConfigException("loadAs");
                }
            };
        }

        if (testConfig.getMatrixType().equals("bi_dimensional_array_backed")) {
            return (Function<Matrix.Dimension, Matrix>) o -> {
                switch (testConfig.getLoadAs()) {
                    case "int":
                        return new BiDimensionalArrayBackedMatrix<Integer>(o);
                    case "double":
                        return new BiDimensionalArrayBackedMatrix<Double>(o);
                    case "complex":
                        return new BiDimensionalArrayBackedMatrix<Complex>(o);
                    default:
                        throw invalidConfigException("loadAd");
                }
            };
        }

        throw invalidConfigException("matrixType");
    }
}
