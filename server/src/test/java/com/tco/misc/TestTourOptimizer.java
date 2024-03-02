package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.tco.requests.Places;
import com.tco.requests.Place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestTourOptimizer {

    final Place origin = new Place("0", "0");
    final Place e90 = new Place("0", "90");
    final Place w90 = new Place("0", "-90");
    final Place e180 = new Place("0", "180");
    final Place w180 = new Place("0", "-180");
    final Place n90 = new Place("90", "0");
    final Place s90 = new Place("-90", "0");
    final Place n45 = new Place("45", "0");
    final Place s45 = new Place("-45", "0");
    final Place e45 = new Place("0", "45");
    final Place w45 = new Place("0", "-45");
    final Place e45n45 = new Place("45", "45");
    final Place e90n90 = new Place("90", "90");

    // to test minimum earth radius value
    final static Double small = 1.0;
    final static long piSmall = Math.round(Math.PI * small);
    final static long piSmallHalf = Math.round(Math.PI / 2.0 * small);

    // to test large values with significant digits to verify double/long
    final static Double big = 1000000000000.0;
    final static long piBig = Math.round(Math.PI * big);
    final static long piBigHalf = Math.round(Math.PI / 2.0 * big);

    TourOptimizer optimizer;
    Places places;

    @BeforeEach
    public void setUpOptimizer() {
        places = new Places();
    }

    @Test
    @DisplayName("bscheidt: distance matrix should be empty")
    public void testEmptyDistanceMatrix() {
        optimizer = new OneOpt();
        optimizer.fillDistanceMatrix(1.0, "vincenty", places);

        assertArrayEquals(new long[0][0], optimizer.getDistanceMatrix());
    }

    @Test
    @DisplayName("bscheidt: distance matrix should have only one element, 0")
    public void testOnePlaceForDistanceMatrix() {
        optimizer = new OneOpt();
        places.add(origin);
        optimizer.fillDistanceMatrix(1.0, "vincenty", places);
        long[][] testMatrix = new long[][] {
            {0}
        };

        assertArrayEquals(testMatrix, optimizer.getDistanceMatrix());
    }

    @Test
    @DisplayName("bscheidt: distance matrix with 2 places")
    public void testTwoPlacesForDistanceMatrix() {
        optimizer = new OneOpt();
        places.add(origin);
        places.add(e180);
        optimizer.fillDistanceMatrix(big, "vincenty", places);
        long[][] testMatrix = new long[][] {
            {0, piBig},
            {piBig, 0}
        };

        assertArrayEquals(testMatrix, optimizer.getDistanceMatrix());
    }

    @Test
    @DisplayName("bscheidt: distance matrix with 3 places")
    public void testThreePlacesForDistanceMatrix() {
        optimizer = new OneOpt();
        places.add(origin);
        places.add(e180);
        places.add(e90);
        optimizer.fillDistanceMatrix(big, "vincenty", places);
        long[][] testMatrix = new long[][] {
            {0, piBig, piBigHalf},
            {piBig, 0, piBigHalf},
            {piBigHalf, piBigHalf, 0},
        };

        assertArrayEquals(testMatrix, optimizer.getDistanceMatrix());
    }

    @Test
    @DisplayName("bscheidt: distance matrix with multiple places")
    public void testMultiplePlacesForDistanceMatrix() {
        optimizer = new OneOpt();
        // a 6 degree difference in coordinates corresponds to 1 unit distance difference when earthRadius is 10
        places.add(origin);
        places.add(new Place("0", "6"));
        places.add(new Place("0", "12"));
        places.add(new Place("0", "18"));
        places.add(new Place("0", "24"));
        optimizer.fillDistanceMatrix(10.0, "vincenty", places);
        long[][] testMatrix = new long[][] {
            {0, 1, 2, 3, 4},
            {1, 0, 1, 2, 3},
            {2, 1, 0, 1, 2},
            {3, 2, 1, 0, 1},
            {4, 3, 2, 1, 0},
        };

        assertArrayEquals(testMatrix, optimizer.getDistanceMatrix());
    }

    @Test
    @DisplayName("lewi0027: testing default returns for nearestNeighbor")
    public void testNearestNeighborWithSimpleArray() {
        long[][] testMatrix = {
            {0, 10, 20, 30},
            {10, 0, 15, 25},
            {20, 15, 0, 10},
            {30, 25, 10, 0}
        };
        int[] testArray = {0, 0, 0, 0};

        optimizer = new OneOpt();
        optimizer.setDistanceMatrix(testMatrix);
        optimizer.setTour(testArray);

        int[] expectedArray = {0, 1, 2, 3}; // Expected nearest neighbors from starting point 0

        assertArrayEquals(expectedArray, optimizer.nearestNeighbor(0));
    }

    @Test
    @DisplayName("lewi0027: testing default returns for nearestNeighbor")
    public void testNearestNeighborWithSimpleArrayReverse() {
        long[][] testMatrix = {
            {0, 10, 20, 30},
            {10, 0, 15, 25},
            {20, 15, 0, 10},
            {30, 25, 10, 0}
        };
        int[] testArray = {0, 0, 0, 0};

        optimizer = new OneOpt();
        optimizer.setDistanceMatrix(testMatrix);
        optimizer.setTour(testArray);

        int[] expectedArray = {3, 2, 1, 0}; // Expected nearest neighbors from starting point 0

        assertArrayEquals(expectedArray, optimizer.nearestNeighbor(3));
    }
}
