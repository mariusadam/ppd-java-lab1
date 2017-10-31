package com.ubb.ppd.lab1.matrix;

import com.ubb.ppd.lab1.Matrix;

import java.util.Objects;

/**
 * @author Marius Adam
 */
@SuppressWarnings("unchecked")
public class UniDimensionalArrayBackedMatrix<T> extends Matrix<T> {
    private Object[] values;

    public UniDimensionalArrayBackedMatrix(Dimension d) {
        super(d);
        values = new Object[this.getLinesCount() * this.getColumnsCount()];
    }

    public UniDimensionalArrayBackedMatrix(Integer linesCount, Integer columnsCount) {
        this(new Dimension(linesCount, columnsCount));
    }

    @Override
    public T get(Integer line, Integer col) {
        return (T) values[offset(line, col)];
    }

    private Integer offset(Integer line, Integer col) {
        return line * getColumnsCount() + col;
    }

    @Override
    public void set(Integer line, Integer col, T value) {
        values[offset(line, col)] = value;
    }

    @Override
    public T get(Integer position) {
        return (T) values[position];
    }

    @Override
    public void set(Integer position, T value) {
        values[position] = value;
    }
}
