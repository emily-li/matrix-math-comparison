package com.liemily.math.matrix;

import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;

import java.util.Arrays;

class OjAlgoMatrixCalculator {
    double[] getNorms(final BasicMatrix matrix) {
        double[] norms = new double[(int) matrix.countRows()];
        for (int i = 0; i < norms.length; i++) {
            norms[i] = matrix.selectRows(i).norm();
        }
        return norms;
    }

    BasicMatrix normalise(final BasicMatrix matrix) {
        final double[] normArray = getNorms(matrix);
        final double[][] norms = new double[(int) matrix.countRows()][(int) matrix.countColumns()];
        for (int i = 0; i < normArray.length; i++) {
            Arrays.fill(norms[i], normArray[i]);
        }
        return matrix.divideElements(PrimitiveMatrix.FACTORY.rows(norms));
    }

    BasicMatrix cosineSimilarity(final BasicMatrix matrix) {
        final BasicMatrix normed = normalise(matrix);
        return normed.multiply(normed.transpose());
    }

    BasicMatrix cosineDistances(final BasicMatrix matrix) {
        BasicMatrix distances = cosineSimilarity(matrix);
        distances = distances.multiply(-1);
        distances = distances.add(1);
        return correctSelfDistances(distances);
    }

    BasicMatrix correctSelfDistances(final BasicMatrix matrix) {
        double[][] corrector = new double[(int) matrix.countRows()][(int) matrix.countRows()];
        for (int i = 0; i < corrector.length; i++) {
            Arrays.fill(corrector[i], 1);
            corrector[i][i] = 0;
        }
        return matrix.multiplyElements(PrimitiveMatrix.FACTORY.rows(corrector));
    }
}
