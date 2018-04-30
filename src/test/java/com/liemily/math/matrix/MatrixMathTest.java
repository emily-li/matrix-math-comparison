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

    @Test
    public void testCosineSimilarity() {
        final double[][] expected = plainJavaMatrixCalculator.cosineSimilarity(matrix);
        final SimpleMatrix ejml = ejmlMatrixCalculator.cosineSimilarity(new SimpleMatrix(matrix));
        final BasicMatrix ojAlgo = ojAlgoMatrixCalculator.cosineSimilarity(PrimitiveMatrix.FACTORY.rows(matrix));
        assertMatricesEqual(expected, ejml, ojAlgo);
    }

    @Test
    public void testCosineDistances() {
        final double[][] expected = plainJavaMatrixCalculator.cosineDistances(matrix);
        final SimpleMatrix ejml = ejmlMatrixCalculator.cosineDistances(new SimpleMatrix(matrix));
        final BasicMatrix ojAlgo = ojAlgoMatrixCalculator.cosineDistances(PrimitiveMatrix.FACTORY.rows(matrix));
        assertMatricesEqual(expected, ejml, ojAlgo);
    }

    @Test
    public void testCorrectSelfDistances() {
        final double[][] expected = plainJavaMatrixCalculator.correctSelfDistances(matrix);
        final SimpleMatrix ejml = ejmlMatrixCalculator.correctSelfDistances(new SimpleMatrix(matrix));
        final BasicMatrix ojAlgo = ojAlgoMatrixCalculator.correctSelfDistances(PrimitiveMatrix.FACTORY.rows(matrix));
        assertMatricesEqual(expected, ejml, ojAlgo);
    }

    private void assertMatricesEqual(final double[][] expected, SimpleMatrix ejmlMatrix, BasicMatrix ojMatrix) {
        assertTrue(new SimpleMatrix(expected).isIdentical(new SimpleMatrix(ejmlMatrix), 0.000000001));
        assertEquals(PrimitiveMatrix.FACTORY.rows(expected), (ojMatrix));
    }
}
