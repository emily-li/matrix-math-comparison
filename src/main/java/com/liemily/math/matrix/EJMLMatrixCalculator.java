package com.liemily.math.matrix;

import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;

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

    SimpleMatrix cosineSimilarity(final SimpleMatrix matrix) {
        final SimpleMatrix normed = normalise(matrix);
        return normed.mult(normed.transpose());
    }

    SimpleMatrix cosineDistances(final SimpleMatrix matrix) {
        SimpleMatrix distances = cosineSimilarity(matrix);
        final double[][] negMultiplier = new double[matrix.numRows()][matrix.numCols()];
        for (double[] aNegMultiplier : negMultiplier) {
            Arrays.fill(aNegMultiplier, -1);
        }
        distances = distances.elementDiv(new SimpleMatrix(negMultiplier));
        distances = distances.plus(1);
        return correctSelfDistances(distances);
    }

    SimpleMatrix correctSelfDistances(final SimpleMatrix matrix) {
        final SimpleMatrix corrected = matrix.copy();
        for (int i = 0; i < corrected.numRows(); i++) {
            corrected.set(i, i, 0);
        }
        return corrected;
    }
}
