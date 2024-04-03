package com.tco.query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoundaryFinder {
    BoundaryFinder boundFinderOne;
    BoundaryFinder boundFinderTwo;

    public TestBoundaryFinder(){
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

    
}
