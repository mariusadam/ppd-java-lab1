package com.ubb.ppd.lab1.framework;

/**
 * @author Marius Adam
 */
public class TestResult {
    private TimeMetric metric;
    private Configuration.TestConfig config;
    private Exception exception;

    public TestResult(Configuration.TestConfig config) {
        this.config = config;
    }

    public TimeMetric getMetric() {
        return metric;
    }

    public Configuration.TestConfig getConfig() {
        return config;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setMetric(TimeMetric metric) {
        this.metric = metric;
    }

    public Exception getException() {
        return exception;
    }
}
