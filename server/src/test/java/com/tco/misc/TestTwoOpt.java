package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.tco.requests.Places;
import com.tco.requests.Place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class TestTwoOpt {
    private TwoOpt optimizer2;
    TourOptimizer optimizer;
    Places places;
    long[][] distanceMatrix;
    Place one = new Place("35.26804693351555", "-106.78710937500001");
    Place two = new Place("35.37561413174875", "-99.71191406250001");
    Place three = new Place("40.36747374615593", "-94.13085937500001");
    Place four = new Place("44.57873024377564", "-99.27246093750001");
    Place five = new Place("44.51609322284931", "-108.32519531250001");
    Place six = new Place("39.863371338285305", "-112.58789062500001");
    int A = 0;
    int B = 1;
    int C = 2;
    int D = 3;
    int E = 4;
    int F = 5;
    

    @BeforeEach
    public void setupTestTwoOptClass() {
        optimizer2 = new TwoOpt();
        optimizer = new TwoOpt();
        places = new Places();
        places.add(one);
        places.add(two);
        places.add(three);
        places.add(four);
        places.add(five);
        places.add(six);
        optimizer.fillDistanceMatrix(3959.0, "vincenty", places);
        distanceMatrix = optimizer.getDistanceMatrix();
        
    }

    // 5 places test

    @Test
    @DisplayName("Wyattg5: Distance of starting tour = 12 optimized = 8")
    public void testTwoOpt5Places(){
        long[][] distanceMatrix = {
            {0, 1, 2, 3, 4}, 
            {1, 0, 1, 2, 3}, 
            {2, 1, 0, 1, 2}, 
            {3, 2, 1, 0, 1}, 
            {4, 3, 2, 1, 0}
        };
        int [] tour = {0, 4, 1, 3, 2};
        optimizer2.setDistanceMatrix(distanceMatrix);
        optimizer2.setTour(tour);

        optimizer2.improve();

        int [] optimizedTour = {0, 1, 4, 3, 2};
        int [] returnedTour = optimizer2.getTour();

        assertArrayEquals(optimizedTour, returnedTour);
    }
    @Test
    @DisplayName("bscheidt: test ACBDEF tour")
    public void testOneSegSwap() {
        optimizer.setTour(new int[]{A, C, B, D, E, F});
        int[] testTour = {A, B, C, D, E, F};

        optimizer.improve();

        assertArrayEquals(testTour, optimizer.getTour());
    }

    @Test
    @DisplayName("bscheidt: test ADCBEF tour")
    public void testTwoSegSwap() {
        optimizer.setTour(new int[]{A, D, C, B, E, F});
        int[] testTour = {A, B, C, D, E, F};

        optimizer.improve();

        assertArrayEquals(testTour, optimizer.getTour());
    }

    @Test
    @DisplayName("bscheidt: test AEDBCF tour")
    public void testOneSegSwapTwo() {
        optimizer.setTour(new int[]{A, E, D, B, C, F});
        int[] testTour = {A, B, C, D, E, F};

        optimizer.improve();

        assertArrayEquals(testTour, optimizer.getTour());
    }
}
