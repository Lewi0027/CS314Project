package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDistances {
    Distances distances;

    @BeforeEach
    public void createDistances() {
        distances = new Distances();
    }

    @Test
    @DisplayName("lewi0027: total() returns Long.MAX_VALUE if sum exceeds Long.MAX_VALUE")
    public void testTotalSumOverflow() {
        distances.add(Long.MAX_VALUE);
        distances.add(3L);
        assertEquals(Long.MAX_VALUE, distances.total());
    }

    @Test
    @DisplayName("lewi0027: total() returns 0 if the distance is negative")
    public void testTotalNegative() {
        distances.add(-1L);
        assertEquals(0L, distances.total());
    }

    @Test
    @DisplayName("lewi0027: total() returns 0 if the distance is greater than Long.MAX_VALUE")
    public void testTotalOverflow() {
        distances.add(Long.MAX_VALUE + 1L);
        assertEquals(0L, distances.total());
    }

    @Test
    @DisplayName("lewi0027: total() returns the sum of multiple Long values")
    public void testLogicalValues() {
        distances.add(5L);
        distances.add(10L);
        distances.add(20L);
        assertEquals(35L, distances.total());
    }
}
