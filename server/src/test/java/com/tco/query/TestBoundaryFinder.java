package com.tco.query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    @DisplayName("diegocel: test calculateBoundary() for correct output")
    public void testCalculateBoundary(){
        boundFinderOne.calculateBoundary();

        assertEquals(13.0, boundFinderOne.getLatMin(50));
        assertEquals(17.0, boundFinderOne.getLatMax(50));
        assertEquals(26.086359063295717, boundFinderOne.getLonMin(50));
        assertEquals(43.91364093670428, boundFinderOne.getLonMax(50));
    }
    
}
