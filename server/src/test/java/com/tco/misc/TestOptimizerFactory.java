package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOptimizerFactory {

    final static int smallPlaceSize = 10;
    final static int mediumPlaceSize = 50;
    final static int largePlaceSize = 100;
    final static int veryLargePlaceSize = 1000;

    OptimizerFactory testClass;

    @BeforeEach
    public void setupTestClass() {
        testClass = new OptimizerFactory();
    }

    // Tests for calculateMatrixTime(int placesSize)
    @Test
    @DisplayName("ajlei: should return about 0.15443702 for placesSize = 10")
    void testSmallInput() {
        int placesSize = smallPlaceSize;
        double expectedTime = 0.15443702;
        assertEquals(expectedTime, testClass.calculateMatrixTime(placesSize), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 0.5321608 for placesSize = 50")
    void testMediumInput() {
        int placesSize = mediumPlaceSize;
        double expectedTime = 0.5321608;
        assertEquals(expectedTime, testClass.calculateMatrixTime(placesSize), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 1.55203267 for placesSize = 100")
    void testLargeInput() {
        int placesSize = largePlaceSize;
        double expectedTime = 1.55203267;
        assertEquals(expectedTime, testClass.calculateMatrixTime(placesSize), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 112.84348 for placesSize = 1000")
    void testVeryLargeInput() {
        int placesSize = veryLargePlaceSize;
        double expectedTime = 112.84348;
        assertEquals(expectedTime, testClass.calculateMatrixTime(placesSize), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 0.12237 for placesSize = 0")
    void testZeroInput() {
        int placesSize = 0;
        double expectedTime = 0.12237; // Assuming constant term for zero input
        assertEquals(expectedTime, testClass.calculateMatrixTime(placesSize), 0.00001);
    }

}