package com.ubb.ppd.lab1;

/**
 * @author Marius Adam
 */
@SuppressWarnings("unchecked")
public class BiDimensionalArrayBackedMatrix<T> extends Matrix<T> {
    private Object[][] values;

    protected BiDimensionalArrayBackedMatrix(Dimension d) {
        super(d);
        values = new Object[d.getLinesCount()][d.getColumnsCount()];
    }

    @Override
    T get(Integer line, Integer col) {
        return (T) values[line][col];
    }

    @Override
    T get(Integer position) {
        int col = position % getLinesCount();
        int line = position / getColumnsCount() + col % 2;
        return get(line, col);
    }

    @Override
    void set(Integer line, Integer col, T value) {
        values[line][col] = value;
    }

    @Override
    void set(Integer position, T value) {
        int col = position % getLinesCount();
        int line = position / getColumnsCount() + col % 2;
        set(line, col, value);
    }
}
