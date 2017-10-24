package com.ubb.ppd.lab1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author Marius Adam
 */
public abstract class Matrix<T> {
    private Integer linesCount;
    private Integer columnsCount;

    protected Matrix(Dimension d) {
        this.linesCount = d.getLinesCount();
        this.columnsCount = d.getColumnsCount();
    }

    public Integer getColumnsCount() {
        return columnsCount;
    }

    public Integer getLinesCount() {
        return linesCount;
    }


    abstract T get(Integer line, Integer col);
    abstract T get(Integer position);
    abstract void set(Integer line, Integer col, T value);
    abstract void set(Integer position, T value);


    public Matrix<T> map(ElementMapper<T> mapFunction) {
        for (int i = 0; i < getLinesCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                set(i, j, mapFunction.apply(i, j));
            }
        }

        return this;
    }

    public boolean canBeAdded(Matrix<T> other) {
        return Objects.equals(other.getColumnsCount(), this.getColumnsCount())
                && Objects.equals(other.getLinesCount(), this.getColumnsCount());
    }

    public boolean canBeMultiplied(Matrix<T> other) {
        return Objects.equals(this.getColumnsCount(), other.getLinesCount());
    }


    @FunctionalInterface
    public static interface ElementMapper<R> {
        public R apply(Integer line, Integer column);
    }

    public static class Dimension {
        private Integer linesCount;
        private Integer columnsCount;

        Dimension(Integer linesCount, Integer columnsCount) {
            this.linesCount = linesCount;
            this.columnsCount = columnsCount;
        }

        public Integer getLinesCount() {
            return linesCount;
        }

        public Integer getColumnsCount() {
            return columnsCount;
        }
    }
}
