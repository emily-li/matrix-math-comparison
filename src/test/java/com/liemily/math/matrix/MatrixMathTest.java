package com.liemily.math.matrix;

import org.ejml.simple.SimpleMatrix;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests equivalency between the libraries and plain implementation to ensure usage is correct
 */
public class MatrixMathTest {
    private static PlainJavaMatrixCalculator plainJavaMatrixCalculator;
    private static EJMLMatrixCalculator ejmlMatrixCalculator;
    private static OjAlgoMatrixCalculator ojAlgoMatrixCalculator;

    private double[][] matrix;
    private double[][] matrix2;

    @BeforeClass
    public static void setupBeforeClass() {
        plainJavaMatrixCalculator = new PlainJavaMatrixCalculator();
        ejmlMatrixCalculator = new EJMLMatrixCalculator();
        ojAlgoMatrixCalculator = new OjAlgoMatrixCalculator();
    }

    @Before
    public void setup() {
        matrix = new double[][]{
                {1.5, 1.2, -4.2, 111},
                {-8.1, -0.1, -333, -1.3},
                {3.222, 1.93, 878.2, 17.86},
                {1, 2, 3, 4}
        };
        matrix2 = new double[][]{
                {-25, -26, -31, 83},
                {77.5, 85.5, -247, 84},
                {-222, -223, 652, -207},
                {-1.5, -0.5, 0.5, 1.5}
        };
    }

    @Test
    public void testMultiply() {
        final double[][] expected = plainJavaMatrixCalculator.multiply(matrix, matrix2);
        final SimpleMatrix ejmlMultiplied = new SimpleMatrix(matrix).mult(new SimpleMatrix(matrix2));
        final BasicMatrix ojMultiplied = PrimitiveMatrix.FACTORY.rows(matrix).multiply(PrimitiveMatrix.FACTORY.rows(matrix2));

        assertMatricesEqual(expected, ejmlMultiplied, ojMultiplied);
    }

    @Test
    public void testElemMultiply() {
        final double[][] expected = plainJavaMatrixCalculator.elemMultiply(matrix, matrix2);
        final SimpleMatrix ejmlMultiplied = new SimpleMatrix(matrix).elementMult(new SimpleMatrix(matrix2));
        final BasicMatrix ojMultiplied = PrimitiveMatrix.FACTORY.rows(matrix).multiplyElements(PrimitiveMatrix.FACTORY.rows(matrix2));

        assertMatricesEqual(expected, ejmlMultiplied, ojMultiplied);
    }

    @Test
    public void testNorms() {
        final double[] expected = plainJavaMatrixCalculator.getNorms(matrix);
        assertArrayEquals(expected, ejmlMatrixCalculator.getNorms(new SimpleMatrix(matrix)), 0.001);
        assertArrayEquals(expected, ojAlgoMatrixCalculator.getNorms(PrimitiveMatrix.FACTORY.rows(matrix)), 0.001);
    }

    @Test
    public void testNormalise() {
        final double[][] expected = plainJavaMatrixCalculator.normalise(matrix);
        assertMatricesEqual(expected, ejmlMatrixCalculator.normalise(new SimpleMatrix(matrix)), ojAlgoMatrixCalculator.normalise(PrimitiveMatrix.FACTORY.rows(matrix)));
    }

    @Test
    public void testTranspose() {
        final double[][] expected = plainJavaMatrixCalculator.transpose(matrix);
        final SimpleMatrix ejml = new SimpleMatrix(matrix).transpose();
        final BasicMatrix ojAlgo = PrimitiveMatrix.FACTORY.rows(matrix).transpose();
        assertMatricesEqual(expected, ejml, ojAlgo);
    }

    private void assertMatricesEqual(final double[][] expected, SimpleMatrix ejmlMatrix, BasicMatrix ojMatrix) {
        assertTrue(new SimpleMatrix(expected).isIdentical(new SimpleMatrix(ejmlMatrix), 0.000000001));
        assertEquals(PrimitiveMatrix.FACTORY.rows(expected), (ojMatrix));
    }


    private void assertDoubleMatrixEquals(final double[][] expectedMatrix, final double[][] actualMatrix) {
        assertEquals(expectedMatrix.length, actualMatrix.length);
        assertEquals(expectedMatrix[0].length, actualMatrix[0].length);
        for (int i = 0; i < expectedMatrix.length; i++) {
            for (int j = 0; j < expectedMatrix[0].length; j++) {
                assertEquals("Co-ordinates did not match: i=" + i + ", j=" + j, expectedMatrix[i][j], actualMatrix[i][j], 0.001);
            }
        }
    }
/*
    public void testCosineSimilarity() {
        final double[][] expectedMatrix = new double[][]{
                {1, 0.07168, 0.06349, 0.05976},
                {0.07168, 1, 0.03297, 0.02943},
                {0.06349, 0.03297, 1, 0.02181},
                {0.05976, 0.02943, 0.02181, 1}
        };
        final double[][] cosineSimilarity = cosineSimilarity(matrix);
        assertMatrixEquals(expectedMatrix, cosineSimilarity);
    }

    double[][] cosineSimilarity(final double[][] matrix) {
        double[][] normedMatrix = matrix.clone();
        normedMatrix = normalise(normedMatrix);
        double[][] transposedMatrix = transpose(normedMatrix);
        return plainJavaMatrixCalculator.multiply(normedMatrix, transposedMatrix);
    }


    double[] getNorms(final double[][] matrix) {
        double[][] squares = elementWiseMultiply(matrix, matrix);
        return Arrays.stream(squares)
                .mapToDouble(vector -> DoubleStream.of(vector).sum())
                .map(Math::sqrt)
                .toArray();
    }

    public double[][] elementWiseMultiply(final double[][] matrix1, final double[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            throw new RuntimeException("Matrices must match in dimension for element wise multiplication");
        }
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
    }*/
}
