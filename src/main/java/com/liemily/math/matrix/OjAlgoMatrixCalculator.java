package com.liemily.math.matrix;

import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;

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
        BasicMatrix norms = PrimitiveMatrix.FACTORY.columns(normArray);
        for (int i = 1; i < normArray.length - 1; i++) {
            norms = norms.mergeRows(norms);
        }
        return matrix.divideElements(norms);
    }

    BasicMatrix cosineSimilarity(final BasicMatrix matrix) {
        final BasicMatrix normed = normalise(matrix);
        return normed.multiply(normed.transpose());
    }
}
