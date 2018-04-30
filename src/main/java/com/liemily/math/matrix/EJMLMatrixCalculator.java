package com.liemily.math.matrix;

import org.ejml.simple.SimpleMatrix;

public class EJMLMatrixCalculator {
    double[] getNorms(final SimpleMatrix matrix) {
        double[] norms = new double[matrix.numRows()];
        for (int i = 0; i < norms.length; i++) {
            norms[i] = matrix.rows(i, i + 1).normF();
        }
        return norms;
    }
}
