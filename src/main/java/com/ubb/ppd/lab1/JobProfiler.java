package com.ubb.ppd.lab1;

/**
 * @author Marius Adam
 */
public class JobProfiler {
    public JobProfiler() {
    }

    public TimeMetric run(Runnable job) {
        long startTime = System.currentTimeMillis();
        job.run();
        return new TimeMetric(System.currentTimeMillis() - startTime);
    }
}
