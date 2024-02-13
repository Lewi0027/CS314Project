package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestHaversine {
    
    static class Geo implements GeographicCoordinate {
        Double latitude;
        Double longitude;

        public Geo(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        public Double latRadians() {
            return Math.toRadians(latitude);
        }

        @Override
        public Double lonRadians() {
            return Math.toRadians(longitude);
        }
    }

    final Geo origin = new Geo(0, 0);
    final Geo n180 = new Geo(180, 0);
    final Geo s180 = new Geo(-180, 0);
    final Geo e180 = new Geo(0, 180);
    final Geo w180 = new Geo(0, -180);
    final Geo e90 = new Geo(0, 90);
    final Geo w90 = new Geo(0, -90);
    final Geo n90 = new Geo(90, 0);
    final Geo s90 = new Geo(-90, 0);
    final Geo n45 = new Geo(45, 0);
    final Geo s45 = new Geo(-45, 0);
    final Geo e45 = new Geo(0, 45);
    final Geo w45 = new Geo(0, -45);
    final Geo e45n45 = new Geo(45, 45);
    final Geo e90n90 = new Geo(90, 90);
    final Geo e135n135 = new Geo(135, 135);

    // to test minimum earth radius value
    final static long small = 1L;
    final static long piSmall = Math.round(Math.PI * small);
    final static long piSmallHalf = Math.round(Math.PI / 2.0 * small);

    // to test large values with significant digits to verify double/long
    final static long big = 1000000000000L;
    final static long piBig = Math.round(Math.PI * big);
    final static long piBigHalf = Math.round(Math.PI / 2.0 * big);

    // to test realistic earth values
    final static Double earthRadiusKM = 6371.0;
    final static Double earthRadiusMi = 3958.8;
    final static Double earthRadiusNM = 3443.92;
    final static Double sampleRadius = 1000.0;

    Haversine testClass;

    @BeforeEach
    public void setupTestClass() {
        testClass = new Haversine();
    }

    // big identity tests

    @Test
    @DisplayName("lewi0027: distance to self should be zero")
    public void testDistanceFromOriginToOrigin() {
        assertEquals(0L, testClass.between(origin, origin, big));
        assertEquals(0L, testClass.between(origin, origin, small));
    }

    @Test
    @DisplayName("lewi0027: distance to same place should be zero")
    public void testDistanceFromE180ToW180() {
        assertEquals(0L, testClass.between(e180, w180, big));
        assertEquals(0L, testClass.between(e180, w180, small));
    }

    @Test
    @DisplayName("lewi0027: origin to *180 similarity")
    public void testDistanceAll180() {
        Long north = testClass.between(origin, n180, sampleRadius);
        Long south = testClass.between(origin, s180, sampleRadius);
        Long east = testClass.between(origin, e180, sampleRadius);
        Long west = testClass.between(origin, w180, sampleRadius);
        assertEquals(north, south);
        assertEquals(south, east);
        assertEquals(east, west);
    }


    // lat or long change tests, just one

    @Test
    @DisplayName("lewi0027: distance from origin to n180")
    public void testDistanceFromOriginToN180() {
        assertEquals(piSmall, testClass.between(origin, n180, small));
        assertEquals(piBig, testClass.between(origin, n180, big));
    }

    @Test
    @DisplayName("lewi0027: distance from origin to n90")
    public void testDistanceFromOriginToN90() {
        assertEquals(piSmallHalf, testClass.between(origin, n90, small));
        assertEquals(piBigHalf, testClass.between(origin, n90, big));
    }

    @Test
    @DisplayName("lewi0027: distance from origin to e180")
    public void testDistanceFromOriginToE180() {
        assertEquals(piSmall, testClass.between(origin, e180, small));
        assertEquals(piBig, testClass.between(origin, e180, big));
    }

    @Test
    @DisplayName("lewi0027: distance from origin to e90")
    public void testDistanceFromOrigintoE90() {
        assertEquals(piSmallHalf, testClass.between(origin, e90, small));
        assertEquals(piBigHalf, testClass.between(origin, e90, big));
    }

    // test real distances

    @Test
    @DisplayName("lewi0027: distance from Fargo to Christchurch in all three units")
    public void testRealDistances() {
        Geo fargo = new Geo(46.8772, -96.7898);
        Geo christchurch = new Geo(-43.5257, 172.6398);

        assertEquals(13399, testClass.between(fargo, christchurch, earthRadiusKM));
        assertEquals(8326, testClass.between(fargo, christchurch, earthRadiusMi));
        assertEquals(7243, testClass.between(fargo, christchurch, earthRadiusNM));
    }

}