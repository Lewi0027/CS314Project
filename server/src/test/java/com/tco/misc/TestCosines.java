package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCosines {

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
    final Geo e180 = new Geo(0, 180);
    final Geo w180 = new Geo(0, -180);
    final Geo n180 = new Geo(180, 0);
    final Geo s180 = new Geo(-180, 0);
    final Geo e90 = new Geo(0, 90);
    final Geo w90 = new Geo(0, -90);
    final Geo n90 = new Geo(90, 0);
    final Geo s90 = new Geo(-90, 0);
    final Geo n45 = new Geo(45, 0);
    final Geo s45 = new Geo(-45, 0);
    final Geo e45 = new Geo(0, 45);
    final Geo w45 = new Geo(0, -45);
    final Geo w45s45 = new Geo(-45, -45);
    final Geo e45n45 = new Geo(45, 45);
    final Geo w90s90 = new Geo(-90, -90);
    final Geo e90n90 = new Geo(90, 90);

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

    Cosines testCosinesClass;

    @BeforeEach
    public void setupTestCosinesClass() {
        testCosinesClass = new Cosines();
    }

    // big identity tests

    @Test
    @DisplayName("Wyattg5: Distance to self should be zero")
    public void testDistanceOriginOrigin() {
        assertEquals( 0L, testCosinesClass.between(origin, origin, big) );
        assertEquals( 0L, testCosinesClass.between(origin, origin, small) );
    }

    @Test
    @DisplayName("Wyattg5: Distance to same place should be zero")
    public void testDistanceSamePlace() {
        assertEquals( 0L, testCosinesClass.between(e180, w180, big) );
        assertEquals( 0L, testCosinesClass.between(e180, w180, small) );
    }

    @Test
    @DisplayName("Wyattg5: Distance from origin to 180 should all be equal")
    public void testDistanceOrigin180() {
        Long north = testCosinesClass.between(origin, n180, sampleRadius);
        Long east = testCosinesClass.between(origin, e180, sampleRadius);
        Long south = testCosinesClass.between(origin, s180, sampleRadius);
        Long west = testCosinesClass.between(origin, w180, sampleRadius);
        assertEquals(north, east);
        assertEquals(east, south);
        assertEquals(south, west);
    }
   
    // Test different regions 
    
    @Test
    @DisplayName("Wyattg5: Distance from origin to w180")
    public void testDistanceOriginW180() {
        assertEquals( piBig, testCosinesClass.between(origin, w180, big) );
        assertEquals( piSmall, testCosinesClass.between(origin, w180, small) );
    }
    
    @Test
    @DisplayName("Wyattg5: Distance from origin to n180")
    public void testDistanceOriginN180() {
        assertEquals(piSmall, testCosinesClass.between(origin, n180, small) );
        assertEquals(piBig, testCosinesClass.between(origin, n180, big) );
    }

    @Test
    @DisplayName("Wyattg5: Distance from origin to e180")
    public void testDistanceOriginE180() {
        assertEquals( piBig, testCosinesClass.between(origin, e180, big) );
        assertEquals( piSmall, testCosinesClass.between(origin, e180, small) );
    }

    @Test
    @DisplayName("Wyattg5: Distance from origin to e90")
    public void testDistanceOriginE90() {
        assertEquals(piSmallHalf, testCosinesClass.between(origin, e90, small) );
        assertEquals(piBigHalf, testCosinesClass.between(origin, e90, big) );
    }

    @Test
    @DisplayName("Wyattg5: Distance from w45 to e45")
    public void testDistanceW45E45(){
        assertEquals( piBigHalf, testCosinesClass.between(w45, e45, big) );
        assertEquals( piSmallHalf, testCosinesClass.between(w45, e45, small) );
    }
    
    // Test real locations

    @Test
    @DisplayName("Wyattg5: Distance from Denver to Beijing")
    public void testDistanceDenvertoBeijing() {
        Geo beijing = new Geo(39.5423, 116.2439);
        Geo denver = new Geo(39.4551, -104.5118);

        assertEquals( 10304, testCosinesClass.between(beijing, denver, earthRadiusKM) );
        assertEquals( 6402, testCosinesClass.between(beijing, denver, earthRadiusMi) );
        assertEquals( 5570, testCosinesClass.between(beijing, denver, earthRadiusNM) );
    }

    //Lewi0027 real distance test

    @Test
    @DisplayName("Wyattg5: Using lewi0027 distance from Fargo to Christchurch")
    public void testRealDistances() {
        Geo fargo = new Geo(46.8772, -96.7898);
        Geo christchurch = new Geo(-43.5257, 172.6398);

        assertEquals(13399, testCosinesClass.between(fargo, christchurch, earthRadiusKM));
        assertEquals(8326, testCosinesClass.between(fargo, christchurch, earthRadiusMi));
        assertEquals(7243, testCosinesClass.between(fargo, christchurch, earthRadiusNM));
    }

}
