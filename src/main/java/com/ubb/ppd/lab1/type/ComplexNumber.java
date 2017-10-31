package com.ubb.ppd.lab1.type;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexFormat;
import org.apache.commons.math3.exception.NullArgumentException;

/**
 * @author Marius Adam
 */
public class ComplexNumber extends Complex {
    private static ComplexFormat complexFormat = new ComplexFormat();

    public ComplexNumber(double real) {
        super(real);
    }

    public ComplexNumber(double real, double imaginary) {
        super(real, imaginary);
    }

    @Override
    protected Complex createComplex(double realPart, double imaginaryPart) {
        return new ComplexNumber(realPart, imaginaryPart);
    }

    public static Complex parse(String s) {
        Complex c = complexFormat.parse(s);
        return new ComplexNumber(c.getReal(), c.getImaginary());
    }

    @Override
    public String toString() {
        return complexFormat.format(this);
    }
}
