package com.ubb.ppd.lab1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Marius Adam
 */
public class Configuration {
    private List<TestConfig> testConfigs;
    private String benchmarksFile;

    public List<TestConfig> getTestConfigs() {
        return testConfigs;
    }

    public void setTestConfigs(List<TestConfig> testConfigs) {
        this.testConfigs = testConfigs;
    }

    @Override
    public String toString() {
        return Arrays.toString(getTestConfigs().toArray());
    }

    public String getBenchmarksFile() {
        return benchmarksFile;
    }

    public void setBenchmarksFile(String benchmarksFile) {
        this.benchmarksFile = benchmarksFile;
    }

    public static class TestConfig {
        private String name;
        private String firstFile;
        private String secondFile;
        private String loadAs;
        private String matrixType;
        private String operation;
        private String benchmarksFile;
        private String computationResultDir;
        private Integer threadsCount;
        private String runType;

        public void setSecondFile(String secondFile) {
            this.secondFile = secondFile;
        }

        public String getLoadAs() {
            return loadAs;
        }

        public String getMatrixType() {
            return matrixType;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getBenchmarksFile() {
            return benchmarksFile;
        }

        public void setBenchmarksFile(String benchmarksFile) {
            this.benchmarksFile = benchmarksFile;
        }

        public String getComputationResultDir() {
            return computationResultDir;
        }

        public void setComputationResultDir(String computationResultDir) {
            this.computationResultDir = computationResultDir;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirstFile() {
            return firstFile;
        }

        public void setFirstFile(String firstFile) {
            this.firstFile = firstFile;
        }

        public String getSecondFile() {
            return secondFile;
        }

        public void setLoadAs(String loadAs) {
            this.loadAs = loadAs;
        }

        public void setMatrixType(String matrixType) {
            this.matrixType = matrixType;
        }

        @Override
        public String toString() {
            return getName()
                    + System.lineSeparator()
                    + getBenchmarksFile()
                    + System.lineSeparator()
                    + getComputationResultDir()
                    + getFirstFile()
                    + System.lineSeparator()
                    + getSecondFile()
                    + System.lineSeparator()
                    + getLoadAs()
                    + System.lineSeparator()
                    + getMatrixType()
                    + System.lineSeparator()
                    + getThreadsCount()
                    ;
        }

        public Integer getThreadsCount() {
            return threadsCount;
        }

        public void setThreadsCount(Integer threadsCount) {
            this.threadsCount = threadsCount;
        }

        public String getRunType() {
            return runType;
        }

        public void setRunType(String runType) {
            this.runType = runType;
        }
    }
}
