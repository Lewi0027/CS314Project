package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestOptimizerFactory {

    final static int smallPlaceSize = 10;
    final static int mediumPlaceSize = 50;
    final static int largePlaceSize = 100;
    final static int veryLargePlaceSize = 1000;
    final static double[] matrixCoeff = {1.69777e-11, -3.28461e-8, 1.26646e-4, 1.94351e-3, 1.2237e-1};
    final static double[] oneOptCoeff = {1e-10, 5.999e-7, -0.000111, 0.4580, 14.9673};
    final static double[] twoOptCoeff = {7.35150e-10, 1.05654e-5, -1.48424e-3, 4.64993e-1, 8.38256};
    final static double[] threeOptCoeff = {3.16846e-6, -2.14276e-4, 3.61923e-2, -1.49333, 19.3161};

    OptimizerFactory testClass;

    @BeforeEach
    public void setupTestClass() {
        testClass = new OptimizerFactory();
    }

    // Tests for createOptimizer(int placesSize, Double response)

    private TourOptimizer result;

    @Test
    @DisplayName("ajlei: should return NoOpt object for response = 0.0 ")
    void testZeroResponse() {
        result = testClass.createOptimizer(10, 0.0);
        assertTrue(result instanceof NoOpt);
    }

    @Test
    @DisplayName("ajlei: should return NoOpt object for response = -1.0 ")
    void testNegativeResponse() {
        result = testClass.createOptimizer(10, -1.0);
        assertTrue(result instanceof NoOpt);
    }

    @Test
    @DisplayName("ajlei: should return NoOpt object for placesSize < 4 ")
    void testLessThanFourResponse() {
        result = testClass.createOptimizer(3, 1.0);
        assertTrue(result instanceof NoOpt);
    }

    @Test
    @DisplayName("ajlei: should return NoOpt object for placesSize = 2600 and response = 1.0")
    void testNormalTooLargeResponseThresholdMatrix() {
        result = testClass.createOptimizer(2600, 1.0);
        assertTrue(result instanceof NoOpt);
    }

    @Test
    @DisplayName("ajlei: should return NoOpt object for response = 0.000001")
    void testTinyTooLargeResponseThresholdMatrix() {
        result = testClass.createOptimizer(1, 0.000001);
        assertTrue(result instanceof NoOpt);
    }

    @Test
    @DisplayName("ajlei: should return NoOpt object for placesSize = 1900 and response = 0.5")
    void testSmallTooLargeResponseThresholdMatrix() {
        result = testClass.createOptimizer(1900, 0.5);
        assertTrue(result instanceof NoOpt);
    }

    @Test
    @DisplayName("ajlei: should return NoOpt object for placesSize = 5100 and response = 10.0")
    void testTooLargeResponseThresholdMatrix() {
        result = testClass.createOptimizer(5100, 10.0);
        assertTrue(result instanceof NoOpt);
    }

    @Test
    @DisplayName("ajlei: should return ThreeOpt object for placesSize = 244 and response = 1.0")
    void testNormalTooLargeResponseThresholdThreeOpt() {
        result = testClass.createOptimizer(244, 1.0);
        assertTrue(result instanceof ThreeOpt);
    }

    @Test
    @DisplayName("ajlei: should return ThreeOpt object for placesSize = 205 and response = 0.5")
    void testSmallTooLargeResponseThresholdThreeOpt() {
        result = testClass.createOptimizer(205, 0.5);
        assertTrue(result instanceof ThreeOpt);
    }

    @Test
    @DisplayName("ajlei: should return ThreeOpt object for placesSize = 432 and response = 10.0")
    void testTooLargeResponseThresholdThreeOpt() {
        result = testClass.createOptimizer(432, 10.0);
        assertTrue(result instanceof ThreeOpt);
    }

    @Test
    @DisplayName("ajlei: should return TwoOpt object for placesSize = 990 and response = 1.0")
    void testNormalTooLargeResponseThresholdTwoOpt() {
        result = testClass.createOptimizer(990, 1.0);
        assertTrue(result instanceof TwoOpt);
    }

    @Test
    @DisplayName("ajlei: should return TwoOpt object for placesSize = 792 and response = 0.5")
    void testSmallTooLargeResponseThresholdTwoOpt() {
        result = testClass.createOptimizer(792, 0.5);
        assertTrue(result instanceof TwoOpt);
    }

    @Test
    @DisplayName("ajlei: should return TwoOpt object for placesSize = 2058 and response = 10.0")
    void testTooLargeResponseThresholdTwoOpt() {
        result = testClass.createOptimizer(2058, 10.0);
        assertTrue(result instanceof TwoOpt);
    }

    @Test
    @DisplayName("ajlei: should return OneOpt object for placesSize = 2258 and response = 1.0")
    void testNormalTooLargeResponseThresholdOneOpt() {
        result = testClass.createOptimizer(2258, 1.0);
        assertTrue(result instanceof OneOpt);
    }

    @Test
    @DisplayName("ajlei: should return OneOpt object for placesSize = 1796 and response = 0.5")
    void testSmallTooLargeResponseThresholdOneOpt() {
        result = testClass.createOptimizer(1796, 0.5);
        assertTrue(result instanceof OneOpt);
    }

    @Test
    @DisplayName("ajlei: should return OneOpt object for placesSize = 4561 and response = 10.0")
    void testTooLargeResponseThresholdOneOpt() {
        result = testClass.createOptimizer(4561, 10.0);
        assertTrue(result instanceof OneOpt);
    }

    @Test
    @DisplayName("ajlei: should return NoOpt object for placesSize = 2259 and response = 1.0")
    void testNormalTooLargeResponseThresholdNoOpt() {
        result = testClass.createOptimizer(2259, 1.0);
        assertTrue(result instanceof NoOpt);
    }

    @Test
    @DisplayName("ajlei: should return NoOpt object for placesSize = 1797 and response = 0.5")
    void testSmallTooLargeResponseThresholdNoOpt() {
        result = testClass.createOptimizer(1797, 0.5);
        assertTrue(result instanceof NoOpt);
    }

    @Test
    @DisplayName("ajlei: should return NoOpt object for placesSize = 4562 and response = 10.0")
    void testTooLargeResponseThresholdNoOpt() {
        result = testClass.createOptimizer(4562, 10.0);
        assertTrue(result instanceof NoOpt);
    }

    // Tests for calculateMatrixTime(int placesSize)
    @Test
    @DisplayName("ajlei: should return about 0.15443702 for placesSize = 10")
    void testMatrixTimeSmallInput() {
        int placesSize = smallPlaceSize;
        double expectedTime = 0.15443702;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, matrixCoeff), 0.00001);
    }


    @Test
    @DisplayName("ajlei: should return about 0.5321608 for placesSize = 50")
    void testMatrixTimeMediumInput() {
        int placesSize = mediumPlaceSize;
        double expectedTime = 0.5321608;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, matrixCoeff), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 1.55203267 for placesSize = 100")
    void testMatrixTimeLargeInput() {
        int placesSize = largePlaceSize;
        double expectedTime = 1.55203267;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, matrixCoeff), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 112.84348 for placesSize = 1000")
    void testMatrixTimeVeryLargeInput() {
        int placesSize = veryLargePlaceSize;
        double expectedTime = 112.84348;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, matrixCoeff), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 0.12237 for placesSize = 0")
    void testMatrixTimeZeroInput() {
        int placesSize = 0;
        double expectedTime = 0.12237; // Assuming constant term for zero input
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, matrixCoeff), 0.00001);
    }

    // Tests calculateOneOpt()

    @Test
    @DisplayName("wgens: should return 14.9673 for placesSize = 0")
    void testOneOptZeroInput() {
        int placesSize = 0;
        double expectedTime = 14.9673; // Assuming constant term for zero input
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, oneOptCoeff), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 1061.86730ish for placesSize = 1000")
    void testOneOptVeryLargeInput() {
        int placesSize = veryLargePlaceSize;
        double expectedTime = 1061.86730;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, oneOptCoeff), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 19.5368009ish for placesSize = 10")
    void testOneOptSmallInput() {
        int placesSize = smallPlaceSize;
        double expectedTime = 19.5368009;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, oneOptCoeff), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 37.6654125ish for placesSize = 50")
    void testOneOptMediumInput() {
        int placesSize = mediumPlaceSize;
        double expectedTime = 37.6654125;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, oneOptCoeff), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 60.2672ish for placesSize = 100")
    void testOneOptLargeInput() {
        int placesSize = largePlaceSize;
        double expectedTime = 60.2672;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, oneOptCoeff), 0.00001);
    }

    // Tests calculateTwoOpt()

    @Test
    @DisplayName("wgens: should return 8.38256 for placesSize = 0")
    void testTwoOptZeroInput() {
        int placesSize = 0;
        double expectedTime = 8.38256; // Assuming constant term for zero input
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, twoOptCoeff), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 10289ish for placesSize = 1000")
    void testTwoOptVeryLargeInput() {
        int placesSize = veryLargePlaceSize;
        double expectedTime = 10289.68556;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, twoOptCoeff), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 12ish for placesSize = 10")
    void testTwoOptSmallInput() {
        int placesSize = smallPlaceSize;
        double expectedTime = 12.894638;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, twoOptCoeff), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 29.2ish for placesSize = 50")
    void testTwoOptMediumInput() {
        int placesSize = mediumPlaceSize;
        double expectedTime = 29.246879;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, twoOptCoeff), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 50ish for placesSize = 100")
    void testTwoOptLargeInput() {
        int placesSize = largePlaceSize;
        double expectedTime = 50.678375;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, twoOptCoeff), 0.00001);
    }

    // Tests calculateThreeOpt()

    @Test
    @DisplayName("bscheidt: should return 19.3161 for placesSize = 0")
    void testThreeOptZeroInput() {
        int placesSize = 0;
        double expectedTime = 19.3161; // Assuming constant term for zero input
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, threeOptCoeff), 0.00001);
    }

    @Test
    @DisplayName("bscheidt: should return 2988902.2860999997 for placesSize = 1000")
    void testThreeOptVeryLargeInput() {
        int placesSize = veryLargePlaceSize;
        double expectedTime = 2988902.2860999997;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, threeOptCoeff), 0.00001);
    }

    @Test
    @DisplayName("bscheidt: should return 7.819438599999998 for placesSize = 10")
    void testThreeOptSmallInput() {
        int placesSize = smallPlaceSize;
        double expectedTime = 7.819438599999998;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, threeOptCoeff), 0.00001);
    }

    @Test
    @DisplayName("bscheidt: should return 28.148724999999992 for placesSize = 50")
    void testThreeOptMediumInput() {
        int placesSize = mediumPlaceSize;
        double expectedTime = 28.148724999999992;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, threeOptCoeff), 0.00001);
    }

    @Test
    @DisplayName("bscheidt: should return 334.4761 for placesSize = 100")
    void testThreeOptLargeInput() {
        int placesSize = largePlaceSize;
        double expectedTime = 334.4761;
        assertEquals(expectedTime, testClass.calculateOptimizationTime(placesSize, threeOptCoeff), 0.00001);
    }
}