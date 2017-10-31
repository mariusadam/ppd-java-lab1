package com.ubb.ppd.lab1.matrix;

import com.ubb.ppd.lab1.Matrix;

/**
 * @author Marius Adam
 */
@SuppressWarnings("unchecked")
public class BiDimensionalArrayBackedMatrix<T> extends Matrix<T> {
    private Object[][] values;

    public BiDimensionalArrayBackedMatrix(Dimension d) {
        super(d);
        values = new Object[d.getLinesCount()][d.getColumnsCount()];
    }

    @Override
    public  T get(Integer line, Integer col) {
        return (T) values[line][col];
    }

    @Override
    public  T get(Integer position) {
        int col = position % getLinesCount();
        int line = position / getColumnsCount();
        return get(line, col);
    }

    @Override
    public void set(Integer line, Integer col, T value) {
        values[line][col] = value;
    }

    @Override
    public  void set(Integer position, T value) {
        int col = position % getLinesCount();
        int line = position / getColumnsCount();
        set(line, col, value);
    }
}
