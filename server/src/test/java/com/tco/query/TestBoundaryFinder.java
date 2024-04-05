package com.tco.query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBoundaryFinder {
    BoundaryFinder boundFinderOne;
    BoundaryFinder boundFinderTwo;

    @BeforeEach
    public void setup(){
        boundFinderOne = new BoundaryFinder(15, 35, 1000, 99);       
        boundFinderTwo = new BoundaryFinder(45, 45, 5000, 9);       
    }

    @Test
    @DisplayName("Wyattg5: Test getLatMin for correct output")
    public void testGetLatMin() {
        assertEquals(13.0, boundFinderOne.getLatMin(50));
        assertEquals(44.0, boundFinderTwo.getLatMin(10));
    }

    @Test
    @DisplayName("Wyattg5: Test getLatMax for correct output")
    public void testGetLatMax() {
        assertEquals(17.0, boundFinderOne.getLatMax(50));
        assertEquals(46.0, boundFinderTwo.getLatMax(10));
    }

    @Test
    @DisplayName("bscheidt: test calculateLatRatio() returns correct calculation")
    public void testCalculateLatRatio() {
        double expected = 17.453292519943293;
        double actual = boundFinderOne.calculateLatRatio();
        assertEquals(expected, actual);
    }

    // degreesOfLongitude(double latByEquator)
    @Test
    @DisplayName("ajlei: test calculateLatRatio() returns for latByEquator = 75")
    public void testPositiveDegreesOfLong() {
        double expected = 21.9160191941;
        double actual = boundFinderOne.degreesOfLongitude(75.0);
        assertEquals(expected, actual, 0.00000001);
    }

    @Test
    @DisplayName("ajlei: test calculateLatRatio() returns for latByEquator = -75")
    public void testNegativeDegreesOfLong() {
        double expected = 21.916015374943928;
        double actual = boundFinderOne.degreesOfLongitude(-75.0);
        assertEquals(expected, actual, 0.00000001);
    }

    @Test
    @DisplayName("diegocel: test calculateBoundary() for correct output")
    public void testCalculateBoundary(){
        boundFinderOne.calculateBoundary();

        assertEquals(13.0, boundFinderOne.getLatMin(50));
        assertEquals(17.0, boundFinderOne.getLatMax(50));
        assertEquals(26.086359063295717, boundFinderOne.getLonMin(50));
        assertEquals(43.91364093670428, boundFinderOne.getLonMax(50));
    }
    
    @DisplayName("bscheidt: test getLonMin() boundary crosses pole")
    public void testGetLonMinCrossesPole() {
        boundFinderOne.setCrossesPoleToTrue();
        double expected = -180;
        double actual = boundFinderOne.getLonMin(20);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("bscheidt: test getLonMin() boundary does not cross pole")
    public void testGetLonMinNotCrossesPole() {
        boundFinderOne.setCrossesPoleToFalse();
        double expected = 28.902710498549705;
        double actual = boundFinderOne.getLonMin(20);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("bscheidt: test getLonMax() boundary crosses pole")
    public void testGetLonMaxCrossesPole() {
        boundFinderOne.setCrossesPoleToTrue();
        double expected = 180;
        double actual = boundFinderOne.getLonMax(20);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("bscheidt: test getLonMax() boundary does not cross pole")
    public void testGetLonMaxNotCrossesPole() {
        boundFinderOne.setCrossesPoleToFalse();
        double expected = 41.0972895014503;
        double actual = boundFinderOne.getLonMax(20);
        assertEquals(expected, actual);
    }  
    @Test
    @DisplayName("Wyattg5: Test constructor for correct output")
    public void testBoundaryFinderConstructor() {
        BoundaryFinder boundfind = new BoundaryFinder(15, 35, 1000, 99);
        assertEquals(15, boundfind.lat);
        assertEquals(35, boundfind.lon);
        assertEquals(1000, boundfind.earthRadius);
        assertEquals(100, boundfind.distance);
        assertTrue(boundfind.getBoundary() instanceof Boundary);
    }
}
