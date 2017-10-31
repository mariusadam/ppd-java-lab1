package com.ubb.ppd.lab1.framework;

import java.io.*;
import java.util.List;

/**
 * @author Marius Adam
 */
public class CsvTestsResultsExporter implements TestsResultsExporter {
    private File destination;
    private static final String HEADER = "Name,Loaded As,Matrix,Operation,Run type,Thread count,Millis";

    public CsvTestsResultsExporter(File file) {
        this.destination = file;
    }

    @Override
    public void export(List<TestResult> results) {
        try (FileWriter fw = new FileWriter(destination);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(HEADER);

            results.forEach(result -> out.println(getLine(result)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getLine(TestResult result) {
        Configuration.TestConfig config = result.getConfig();
        if (result.getException() != null) {
            return String.format(
                    "Found an error while processing test result for %s (%s)",
                    config.getName(),
                    result.getException().getMessage()
            );
        }
        return String.format(
                "%s,%s,%s,%s,%s,%d,%d",
                config.getName(),
                config.getLoadAs(),
                config.getMatrixType(),
                config.getOperation(),
                config.getRunType(),
                config.getThreadsCount(),
                result.getMetric().getMillis()
        );
    }
}
