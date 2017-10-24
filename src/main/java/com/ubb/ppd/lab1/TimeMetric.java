package com.ubb.ppd.lab1;

/**
 * @author Marius Adam
 */
public class TimeMetric {
    private long millis;

    public TimeMetric(long millis) {
        this.millis = millis;
    }

    public long getMillis() {
        return millis;
    }

    public String pretty() {
        long seconds = millis / 1000;
        long millisPart = millis % 1000;

        return String.format("%d(%d seconds, %d millis)", millis, seconds, millisPart);
    }
}
