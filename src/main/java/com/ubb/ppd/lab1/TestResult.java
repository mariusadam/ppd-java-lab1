package com.ubb.ppd.lab1;

import com.ubb.ppd.lab1.Configuration.TestConfig;

/**
 * @author Marius Adam
 */
public class TestResult {
    private TimeMetric metric;
    private TestConfig config;
    private Exception exception;

    public TestResult(TestConfig config) {
        this.config = config;
    }

    public TimeMetric getMetric() {
        return metric;
    }

    public TestConfig getConfig() {
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
