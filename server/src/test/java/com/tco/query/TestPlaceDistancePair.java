package com.tco.query;

import com.tco.requests.Place;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPlaceDistancePair {

    PlaceDistancePair testClass;

    @Test
    @DisplayName("ajlei: Return null for getPlace() and 10 for getDistance()")
    void testNullResponse() {
        testClass = new PlaceDistancePair(null, 10);

        assertEquals(null, testClass.getPlace());
        assertEquals((long)10, testClass.getDistance());
    }

    @Test
    @DisplayName("ajlei: Return an empty Place for getPlace() and 10 for getDistance()")
    void testPlaceResponse() {
        testClass = new PlaceDistancePair(new Place(), 10);

        assertTrue(testClass.getPlace() instanceof Place);
        assertEquals(10, testClass.getDistance());
    }
}