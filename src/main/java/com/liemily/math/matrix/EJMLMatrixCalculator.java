package com.liemily.math.matrix;

import org.ejml.simple.SimpleMatrix;

class EJMLMatrixCalculator {
    double[] getNorms(final SimpleMatrix matrix) {
        double[] norms = new double[matrix.numRows()];
        for (int i = 0; i < norms.length; i++) {
            norms[i] = matrix.rows(i, i + 1).normF();
        }
        return norms;
    }

    SimpleMatrix normalise(final SimpleMatrix matrix) {
        final double[] normArray = getNorms(matrix);
        double[][] norms = new double[matrix.numRows()][1];
        for (int i = 0; i < normArray.length; i++) {
            norms[i][0] = normArray[i];
        }
        SimpleMatrix normMatrix = new SimpleMatrix(norms);
        for (int i = 1; i < normArray.length - 1; i++) {
            normMatrix = normMatrix.concatColumns(normMatrix);
        }
        return matrix.elementDiv(normMatrix);
    }
}
