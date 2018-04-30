package com.liemily.math.matrix;

import org.ojalgo.matrix.BasicMatrix;

public class OjAlgoMatrixCalculator {
    public double[] getNorms(BasicMatrix matrix) {
        double[] norms = new double[(int) matrix.countRows()];
        for (int i = 0; i < norms.length; i++) {
            norms[i] = matrix.selectRows(i).norm();
        }
        return norms;
    }
}
