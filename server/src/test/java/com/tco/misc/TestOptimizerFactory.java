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
    void testMatrixTimeSmallInput() {
        int placesSize = smallPlaceSize;
        double expectedTime = 0.15443702;
        assertEquals(expectedTime, testClass.calculateMatrixTime(placesSize), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 0.5321608 for placesSize = 50")
    void testMatrixTimeMediumInput() {
        int placesSize = mediumPlaceSize;
        double expectedTime = 0.5321608;
        assertEquals(expectedTime, testClass.calculateMatrixTime(placesSize), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 1.55203267 for placesSize = 100")
    void testMatrixTimeLargeInput() {
        int placesSize = largePlaceSize;
        double expectedTime = 1.55203267;
        assertEquals(expectedTime, testClass.calculateMatrixTime(placesSize), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 112.84348 for placesSize = 1000")
    void testMatrixTimeVeryLargeInput() {
        int placesSize = veryLargePlaceSize;
        double expectedTime = 112.84348;
        assertEquals(expectedTime, testClass.calculateMatrixTime(placesSize), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 0.12237 for placesSize = 0")
    void testMatrixTimeZeroInput() {
        int placesSize = 0;
        double expectedTime = 0.12237; // Assuming constant term for zero input
        assertEquals(expectedTime, testClass.calculateMatrixTime(placesSize), 0.00001);
    }


    // Tests for calculateNoOpt(int placesSize)
    @Test
    @DisplayName("ajlei: should return about 20.06500388 for placesSize = 10")
    void testNoOptSmallInput() {
        int placesSize = smallPlaceSize;
        double expectedTime = 20.06500388;
        assertEquals(expectedTime, testClass.calculateNoOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 31.924616 for placesSize = 50")
    void testNoOptMediumInput() {
        int placesSize = mediumPlaceSize;
        double expectedTime = 31.924616;
        assertEquals(expectedTime, testClass.calculateNoOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 46.309658 for placesSize = 100")
    void testNoOptLargeInput() {
        int placesSize = largePlaceSize;
        double expectedTime = 46.309658;
        assertEquals(expectedTime, testClass.calculateNoOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 221.74007 for placesSize = 1000")
    void testNoOptVeryLargeInput() {
        int placesSize = veryLargePlaceSize;
        double expectedTime = 221.74007;
        assertEquals(expectedTime, testClass.calculateNoOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("ajlei: should return about 17.05127 for placesSize = 0")
    void testNoOptZeroInput() {
        int placesSize = 0;
        double expectedTime = 17.05127; // Assuming constant term for zero input
        assertEquals(expectedTime, testClass.calculateNoOpt(placesSize), 0.00001);
    }

    // Tests calculateOneOpt()

    @Test
    @DisplayName("wgens: should return 7.05127 for placesSize = 0")
    void testOneOptZeroInput() {
        int placesSize = 0;
        double expectedTime = 7.85096; // Assuming constant term for zero input
        assertEquals(expectedTime, testClass.calculateOneOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 1054.4ish for placesSize = 1000")
    void testOneOptVeryLargeInput() {
        int placesSize = veryLargePlaceSize;
        double expectedTime = 1054.39496;
        assertEquals(expectedTime, testClass.calculateOneOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 14.16ish for placesSize = 10")
    void testOneOptSmallInput() {
        int placesSize = smallPlaceSize;
        double expectedTime = 14.158997;
        assertEquals(expectedTime, testClass.calculateOneOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 31.9ish for placesSize = 50")
    void testOneOptMediumInput() {
        int placesSize = mediumPlaceSize;
        double expectedTime = 37.665668;
        assertEquals(expectedTime, testClass.calculateOneOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 64ish for placesSize = 100")
    void testOneOptLargeInput() {
        int placesSize = largePlaceSize;
        double expectedTime = 64.011365;
        assertEquals(expectedTime, testClass.calculateOneOpt(placesSize), 0.00001);
    }

    // Tests calculateTwoOpt()

    @Test
    @DisplayName("wgens: should return 8.38256 for placesSize = 0")
    void testTwoOptZeroInput() {
        int placesSize = 0;
        double expectedTime = 8.38256; // Assuming constant term for zero input
        assertEquals(expectedTime, testClass.calculateTwoOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 10289ish for placesSize = 1000")
    void testTwoOptVeryLargeInput() {
        int placesSize = veryLargePlaceSize;
        double expectedTime = 10289.68556;
        assertEquals(expectedTime, testClass.calculateTwoOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 12ish for placesSize = 10")
    void testTwoOptSmallInput() {
        int placesSize = smallPlaceSize;
        double expectedTime = 12.894638;
        assertEquals(expectedTime, testClass.calculateTwoOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 29.2ish for placesSize = 50")
    void testTwoOptMediumInput() {
        int placesSize = mediumPlaceSize;
        double expectedTime = 29.246879;
        assertEquals(expectedTime, testClass.calculateTwoOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("wgens: should return 50ish for placesSize = 100")
    void testTwoOptLargeInput() {
        int placesSize = largePlaceSize;
        double expectedTime = 50.678375;
        assertEquals(expectedTime, testClass.calculateTwoOpt(placesSize), 0.00001);
    }

    // Tests calculateThreeOpt()

    @Test
    @DisplayName("bscheidt: should return 19.3161 for placesSize = 0")
    void testThreeOptZeroInput() {
        int placesSize = 0;
        double expectedTime = 19.3161; // Assuming constant term for zero input
        assertEquals(expectedTime, testClass.calculateThreeOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("bscheidt: should return 2988902.2860999997 for placesSize = 1000")
    void testThreeOptVeryLargeInput() {
        int placesSize = veryLargePlaceSize;
        double expectedTime = 2988902.2860999997;
        assertEquals(expectedTime, testClass.calculateThreeOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("bscheidt: should return 7.819438599999998 for placesSize = 10")
    void testThreeOptSmallInput() {
        int placesSize = smallPlaceSize;
        double expectedTime = 7.819438599999998;
        assertEquals(expectedTime, testClass.calculateThreeOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("bscheidt: should return 28.148724999999992 for placesSize = 50")
    void testThreeOptMediumInput() {
        int placesSize = mediumPlaceSize;
        double expectedTime = 28.148724999999992;
        assertEquals(expectedTime, testClass.calculateThreeOpt(placesSize), 0.00001);
    }

    @Test
    @DisplayName("bscheidt: should return 334.4761 for placesSize = 100")
    void testThreeOptLargeInput() {
        int placesSize = largePlaceSize;
        double expectedTime = 334.4761;
        assertEquals(expectedTime, testClass.calculateThreeOpt(placesSize), 0.00001);
    }


}