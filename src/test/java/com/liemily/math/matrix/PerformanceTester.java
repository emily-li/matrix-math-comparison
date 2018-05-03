package com.liemily.math.matrix;

import org.ejml.simple.SimpleMatrix;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PerformanceTester {
    private static ExecutorService executorService;
    private static int maxParallelRuns;
    private static int totalRuns;

    private static EJMLMatrixCalculator ejmlMatrixCalculator;
    private static OjAlgoMatrixCalculator ojAlgoMatrixCalculator;
    private static PlainJavaMatrixCalculator plainJavaMatrixCalculator;

    private static double[][] doubleMatrix;
    private static double[][] doubleMatrix2;
    private static SimpleMatrix simpleMatrix;
    private static SimpleMatrix simpleMatrix2;
    private static BasicMatrix primitiveMatrix;
    private static BasicMatrix primitiveMatrix2;

    private double avgPlainTime;
    private double avgEjmlTime;
    private double avgOjTime;

    @BeforeClass
    public static void setupBeforeClass() {
        maxParallelRuns = 5;
        executorService = Executors.newFixedThreadPool(maxParallelRuns);
        totalRuns = 100;

        ejmlMatrixCalculator = new EJMLMatrixCalculator();
        ojAlgoMatrixCalculator = new OjAlgoMatrixCalculator();
        plainJavaMatrixCalculator = new PlainJavaMatrixCalculator();

        doubleMatrix = new double[1000][1000];
        doubleMatrix2 = new double[doubleMatrix.length][doubleMatrix.length];

        final Random random = new Random();
        for (int i = 0; i < doubleMatrix.length; i++) {
            doubleMatrix[i] = random.doubles(doubleMatrix.length).toArray();
            doubleMatrix2[i] = random.doubles(doubleMatrix.length).toArray();
        }

        simpleMatrix = new SimpleMatrix(doubleMatrix);
        simpleMatrix2 = new SimpleMatrix(doubleMatrix2);

        primitiveMatrix = PrimitiveMatrix.FACTORY.rows(doubleMatrix);
        primitiveMatrix2 = PrimitiveMatrix.FACTORY.rows(doubleMatrix2);
    }

    @Before
    public void setup() {
        avgPlainTime = 0;
        avgEjmlTime = 0;
        avgOjTime = 0;
    }

    @After
    public void tearDown() {
        System.out.println("Plain:  " + avgPlainTime);
        System.out.println("EJML:   " + avgEjmlTime);
        System.out.println("OjAlgo: " + avgOjTime);
    }

    @Test
    public void testMatrixCreation() throws Exception {
        avgEjmlTime = timeAvg(() -> new SimpleMatrix(doubleMatrix), totalRuns);
        avgOjTime = timeAvg(() -> PrimitiveMatrix.FACTORY.rows(doubleMatrix), totalRuns);

        System.out.println("Average time to parse double matrices of size " + doubleMatrix.length + "*" + doubleMatrix.length);
    }

    @Test
    public void testMultiply() throws Exception {
        avgPlainTime = timeAvg(() -> plainJavaMatrixCalculator.multiply(doubleMatrix, doubleMatrix2), totalRuns);
        avgEjmlTime = timeAvg(() -> simpleMatrix.mult(simpleMatrix2), totalRuns);
        avgOjTime = timeAvg(() -> primitiveMatrix.multiply(primitiveMatrix2), totalRuns);

        System.out.println("Average time to multiply matrices of size " + doubleMatrix.length + "*" + doubleMatrix.length);
    }

    @Test
    public void testElemMultiply() throws Exception {
        avgPlainTime = timeAvg(() -> plainJavaMatrixCalculator.elemMultiply(doubleMatrix, doubleMatrix2), totalRuns);
        avgEjmlTime = timeAvg(() -> simpleMatrix.elementMult(simpleMatrix2), totalRuns);
        avgOjTime = timeAvg(() -> primitiveMatrix.multiplyElements(primitiveMatrix2), totalRuns);

        System.out.println("Average time to element multiply matrices of size " + doubleMatrix.length + "*" + doubleMatrix.length);
    }

    @Test
    public void testTranspose() throws Exception {
        avgPlainTime = timeAvg(() -> plainJavaMatrixCalculator.transpose(doubleMatrix), totalRuns);
        avgEjmlTime = timeAvg(() -> simpleMatrix.transpose(), totalRuns);
        avgOjTime = timeAvg(() -> primitiveMatrix.transpose(), totalRuns);

        System.out.println("Average time to transpose matrix of size " + doubleMatrix.length + "*" + doubleMatrix.length);
    }

    @Test
    public void testCosineSimilarity() throws Exception {
        avgPlainTime = timeAvg(() -> plainJavaMatrixCalculator.cosineSimilarity(doubleMatrix), totalRuns);
        avgEjmlTime = timeAvg(() -> ejmlMatrixCalculator.cosineSimilarity(simpleMatrix), totalRuns);
        avgOjTime = timeAvg(() -> ojAlgoMatrixCalculator.cosineSimilarity(primitiveMatrix), totalRuns);

        System.out.println("Average time to calculate cosine similarity for matrix of size " + doubleMatrix.length + "*" + doubleMatrix.length);
    }

    @Test
    public void testCosineDistances() throws Exception {
        avgPlainTime = timeAvg(() -> plainJavaMatrixCalculator.cosineDistances(doubleMatrix), totalRuns);
        avgEjmlTime = timeAvg(() -> ejmlMatrixCalculator.cosineDistances(simpleMatrix), totalRuns);
        avgOjTime = timeAvg(() -> ojAlgoMatrixCalculator.cosineDistances(primitiveMatrix), totalRuns);

        System.out.println("Average time to calculate cosine distance for matrix of size " + doubleMatrix.length + "*" + doubleMatrix.length);
    }

    @Test
    public void testCorrectSelfDistance() throws Exception {
        avgPlainTime = timeAvg(() -> plainJavaMatrixCalculator.correctSelfDistances(doubleMatrix), totalRuns);
        avgEjmlTime = timeAvg(() -> ejmlMatrixCalculator.correctSelfDistances(simpleMatrix), totalRuns);
        avgOjTime = timeAvg(() -> ojAlgoMatrixCalculator.correctSelfDistances(primitiveMatrix), totalRuns);

        System.out.println("Average time to correct self distances for matrix of size " + doubleMatrix.length + "*" + doubleMatrix.length);
    }

    @Test
    public void testNormalise() throws Exception {
        avgPlainTime = timeAvg(() -> plainJavaMatrixCalculator.normalise(doubleMatrix), totalRuns);
        avgEjmlTime = timeAvg(() -> ejmlMatrixCalculator.normalise(simpleMatrix), totalRuns);
        avgOjTime = timeAvg(() -> ojAlgoMatrixCalculator.normalise(primitiveMatrix), totalRuns);

        System.out.println("Average time to normalise matrix of size " + doubleMatrix.length + "*" + doubleMatrix.length);
    }

    @Test
    public void testGetNorms() throws Exception {
        avgPlainTime = timeAvg(() -> plainJavaMatrixCalculator.getNorms(doubleMatrix), totalRuns);
        avgEjmlTime = timeAvg(() -> ejmlMatrixCalculator.getNorms(simpleMatrix), totalRuns);
        avgOjTime = timeAvg(() -> ojAlgoMatrixCalculator.getNorms(primitiveMatrix), totalRuns);

        System.out.println("Average time to get norms for matrix of size " + doubleMatrix.length + "*" + doubleMatrix.length);
    }

    private long time(Runnable matrixRunnable) {
        final long preTime = System.currentTimeMillis();
        matrixRunnable.run();
        final long postTime = System.currentTimeMillis();
        return postTime - preTime;
    }

    private double timeAvg(Runnable matrixRunnable, int totalRuns) throws Exception {
        final Collection<Callable<Long>> callables = new ArrayList<>();
        for (int i = 0; i < totalRuns; i++) {
            callables.add(() -> time(matrixRunnable));
        }

        long totalTime = 0;
        for (Future<Long> time : executorService.invokeAll(callables)) {
            totalTime += time.get();
        }
        return totalTime / totalRuns;
    }
}
