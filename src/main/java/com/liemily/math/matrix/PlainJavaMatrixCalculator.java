package com.liemily.math.matrix;

import java.util.Arrays;
import java.util.stream.DoubleStream;

class PlainJavaMatrixCalculator {

    double[][] multiply(final double[][] matrix1, final double[][] matrix2) {
        double[][] multiplied = new double[matrix1.length][matrix2[0].length];
        Arrays.stream(multiplied).forEach(row -> Arrays.fill(row, 0.0));

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                for (int k = 0; k < matrix1[0].length; k++) {
                    multiplied[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return multiplied;
    }

    double[][] elemMultiply(final double[][] matrix1, final double[][] matrix2) {
        double[][] multiplied = new double[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2.length; j++) {
                multiplied[i][j] = matrix1[i][j] * matrix2[i][j];
            }
        }
        return multiplied;
    }

    public double[][] transpose(final double[][] matrix) {
        double[][] transposed = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    double[][] normalise(final double[][] matrix) {
        double[][] normalised = copy(matrix);
        double[] norms = getNorms(normalised);
        for (int i = 0; i < normalised.length; i++) {
            for (int j = 0; j < normalised[0].length; j++) {
                normalised[i][j] /= norms[i];
            }
        }
        return normalised;
    }

    private double[][] copy(final double[][] matrix) {
        double[][] clone = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                clone[i][j] = matrix[i][j];
            }
        }
        return clone;
    }

    double[] getNorms(final double[][] matrix) {
        return Arrays.stream(matrix).mapToDouble(this::getNorm).toArray();
    }

    private double getNorm(final double[] vector) {
        return Math.sqrt(DoubleStream.of(vector).map(x -> x * x).sum());
    }
}
