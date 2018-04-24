package com.liemily.math.matrix;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class PlainJavaMatrixCalculator {

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

    double getNorm(final double[] vector) {
        return Math.sqrt(DoubleStream.of(vector).map(x -> x * x).sum());
    }

    double[] getNorms(final double[][] matrix) {
        return Arrays.stream(matrix).mapToDouble(this::getNorm).toArray();
    }
}
