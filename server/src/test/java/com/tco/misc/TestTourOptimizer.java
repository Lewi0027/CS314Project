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
    final Place penta1 = new Place("-14.25", "-49.5");
    final Place penta2 = new Place("49.5", "31.25");
    final Place penta3 = new Place("-9", "15");
    final Place penta4 = new Place("32", "-67.75");
    final Place penta5 = new Place("35", "21.5");

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

    @Test
    @DisplayName("lewi0027: testing nearestNeighbor using places.add()")
    public void testNearestNeighborUsingPlaces() {
        optimizer = new OneOpt();
        places.add(penta1);
        places.add(penta2);
        places.add(penta3);
        places.add(penta4);
        places.add(penta5);

        optimizer.fillDistanceMatrix(10.0, "vincenty", places);

        int[] expectedArray = {0, 3, 1, 4, 2};
        int[] testArray = {0, 0, 0, 0, 0};

        optimizer.setTour(testArray);

        assertArrayEquals(expectedArray, optimizer.nearestNeighbor(0));
    }

    @Test
    @DisplayName("lewi0027: testing nearestNeighbor with only one location")
    public void testNearestNeighborOneLocation() {
        optimizer = new OneOpt();
        places.add(origin);

        optimizer.fillDistanceMatrix(10.0, "vincenty", places);

        int[] expectedArray = {0};

        optimizer.setTour(expectedArray);

        assertArrayEquals(expectedArray, optimizer.nearestNeighbor(0));
    }

    @Test
    @DisplayName("bscheidt: test 0 length tour total distance")
    public void testTotalDistanceZeroLength() {
        optimizer = new OneOpt();
        int[] tour = {};
        optimizer.fillDistanceMatrix(10.0, "vincenty", places);
        optimizer.setTour(tour);

        assertEquals(0l, optimizer.totalDistanceOfTour());
    }

    @Test
    @DisplayName("bscheidt: test 1 length tour total distance")
    public void testTotalDistanceOneLength() {
        optimizer = new OneOpt();
        int[] tour = new int[]{0};
        places.add(e180);
        optimizer.fillDistanceMatrix(1000.0, "vincenty", places);
        optimizer.setTour(tour);

        assertEquals(0l, optimizer.totalDistanceOfTour());
    }

    @Test
    @DisplayName("bscheidt: test 2 length tour total distance")
    public void testTotalDistanceTwoLength() {
        optimizer = new OneOpt();
        int[] tour = new int[]{0, 1};
        places.add(e180);
        places.add(origin);
        optimizer.fillDistanceMatrix(big, "vincenty", places);
        optimizer.setTour(tour);

        assertEquals(piBig * 2, optimizer.totalDistanceOfTour());
    }

    @Test
    @DisplayName("bscheidt: test 5 length tour total distance")
    public void testTotalDistanceFiveLength() {
        optimizer = new OneOpt();
        int[] tour = new int[]{0, 1, 2, 3, 4};
        places.add(origin);
        places.add(e180);
        places.add(n90);
        places.add(s90);
        places.add(w180);
        optimizer.fillDistanceMatrix(big, "vincenty", places);
        optimizer.setTour(tour);
        long totalDistance = piBig + piBigHalf + piBig + piBigHalf + piBig;

        assertEquals(totalDistance, optimizer.totalDistanceOfTour());
    }

    @Test
    @DisplayName("bscheidt: find shortest tour for 0 locations")
    public void testShortestTourZeroPlaces() {
        optimizer = new OneOpt();
        long[][] distanceMatrix = {{}};
        optimizer.setDistanceMatrix(distanceMatrix);
        optimizer.fillTour(places.size());

        optimizer.calculateShortestTour();

        int[] expectedTour = {};
        long expectedDistance = 0l;

        assertArrayEquals(expectedTour, optimizer.getTour());
        assertEquals(expectedDistance, optimizer.totalDistanceOfTour());

    }

    @Test
    @DisplayName("bscheidt: find shortest tour for 1 location")
    public void testShortestTourOnePlace() {
        optimizer = new OneOpt();
        places.add(origin);
        long[][] distanceMatrix = {{0}};
        optimizer.setDistanceMatrix(distanceMatrix);
        optimizer.fillTour(places.size());

        optimizer.calculateShortestTour();

        int[] expectedTour = {0};
        long expectedDistance = 0l;

        assertArrayEquals(expectedTour, optimizer.getTour());
        assertEquals(expectedDistance, optimizer.totalDistanceOfTour());

    }

    @Test
    @DisplayName("bscheidt: find shortest tour for 2 locations")
    public void testShortestTourTwoPlaces() {
        optimizer = new OneOpt();
        places.add(origin);
        places.add(e180);
        long[][] distanceMatrix = {
            {0, piBig},
            {piBig, 0}
        };
        optimizer.setDistanceMatrix(distanceMatrix);
        optimizer.fillTour(places.size());

        optimizer.calculateShortestTour();

        int[] expectedTour = {0, 1};
        long expectedDistance = piBig * 2;

        assertArrayEquals(expectedTour, optimizer.getTour());
        assertEquals(expectedDistance, optimizer.totalDistanceOfTour());

    }

    @Test
    @DisplayName("bscheidt: find shortest tour for 3 locations")
    public void testShortestTourThreePlaces() {
        optimizer = new OneOpt();
        places.add(origin);
        places.add(e180);
        places.add(n90);
        long[][] distanceMatrix = {
            {0, piBig, piBigHalf},
            {piBig, 0, piBigHalf},
            {piBigHalf, piBigHalf, 0}
        };
        optimizer.setDistanceMatrix(distanceMatrix);
        optimizer.fillTour(places.size());

        optimizer.calculateShortestTour();

        int[] expectedTour = {0, 2, 1};
        long expectedDistance = piBig + piBigHalf + piBigHalf;

        assertArrayEquals(expectedTour, optimizer.getTour());
        assertEquals(expectedDistance, optimizer.totalDistanceOfTour());

    }

    @Test
    @DisplayName("bscheidt: shortest tour starts at different location")
    public void testShortestTourDifferentStartPoint() {
        optimizer = new OneOpt();
        // E <--------50--------> C <-1-> B <--2--> A <----10----> D
        
        long[][] distanceMatrix = {
            {0, 2, 3, 10, 53},
            {2, 0, 1, 12, 51},
            {3, 1, 0, 13, 50},
            {10, 12, 13, 0, 63},
            {53, 51, 50, 63, 0}
        };
        optimizer.setDistanceMatrix(distanceMatrix);
        optimizer.fillTour(5);

        optimizer.calculateShortestTour();

        int[] expectedTour = {2, 1, 0, 3, 4};
        long expectedDistance = 126L;

        assertArrayEquals(expectedTour, optimizer.getTour());
        assertEquals(expectedDistance, optimizer.totalDistanceOfTour());

    }

    @Test
    @DisplayName("lewi0027: rearrangePlaces 3 item test")
    public void testRearrangePlacesWithThreeItems() {
        optimizer = new OneOpt();
        optimizer.setTour(new int[]{2,1,0});
        places.add(origin);
        places.add(e90);
        places.add(n90);

        Places rearrangedPlaces = optimizer.rearrangePlaces(places);

        Places expectedPlaces = new Places();
        expectedPlaces.add(n90);
        expectedPlaces.add(e90);
        expectedPlaces.add(origin);

        assertEquals(expectedPlaces, rearrangedPlaces);
    }

    @Test
    @DisplayName("lewi0027: rearrangePlaces 3 item; no change")
    public void testRearrangePlacesWithThreeItemsNoChange() {
        optimizer = new OneOpt();
        optimizer.setTour(new int[]{0,1,2});
        places.add(origin);
        places.add(e90);
        places.add(n90);

        Places rearrangedPlaces = optimizer.rearrangePlaces(places);

        Places expectedPlaces = new Places();
        expectedPlaces.add(origin);
        expectedPlaces.add(e90);
        expectedPlaces.add(n90);

        assertEquals(expectedPlaces, rearrangedPlaces);
    }

    @Test
    @DisplayName("lewi0027: rearrangePlaces with one item; no change")
    public void testRearrangePlacesWithOneItemNoChange() {
        optimizer = new OneOpt();
        optimizer.setTour(new int[]{0});
        places.add(origin);

        Places rearrangedPlaces = optimizer.rearrangePlaces(places);

        Places expectedPlaces = new Places();
        expectedPlaces.add(origin);

        assertEquals(expectedPlaces, rearrangedPlaces);
    }

    @Test
    @DisplayName("lewi0027: findNearestPlace four item test")
    public void testFindNearestPlaceFourItems() {
        optimizer = new OneOpt();
        optimizer.setTour(new int []{0,1,2,3});

        long[][] distanceMatrix = {
            {0, 10, 20, 30},
            {10, 0, 15, 25},
            {20, 15, 0, 10},
            {30, 25, 10, 0}
        };
        
        optimizer.setDistanceMatrix(distanceMatrix);
        boolean[] visited = {true, false, false, false};
        int currentPlace = 0;

        int nearestPlace = optimizer.findNearestPlace(visited, currentPlace);

        assertEquals(1, nearestPlace);
    }

    @Test
    @DisplayName("lewi0027: findNearestPlace one item test; return -1")
    public void testFindNearestPlaceOneItem() {
        optimizer = new OneOpt();
        optimizer.setTour(new int []{0});

        long[][] distanceMatrix = {
            {0}
        };
        
        optimizer.setDistanceMatrix(distanceMatrix);
        boolean[] visited = {true};
        int currentPlace = 0;

        int nearestPlace = optimizer.findNearestPlace(visited, currentPlace);

        assertEquals(-1, nearestPlace);
    }
}
