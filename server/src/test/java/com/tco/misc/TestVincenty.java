package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestVincenty {

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
    final Geo e90 = new Geo(0, 90);
    final Geo w90 = new Geo(0, -90);
    final Geo n90 = new Geo(90, 0);
    final Geo s90 = new Geo(-90, 0);
    final Geo n45 = new Geo(45, 0);
    final Geo s45 = new Geo(-45, 0);
    final Geo e45 = new Geo(0, 45);
    final Geo w45 = new Geo(0, -45);

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

    Vincenty calculator;

    @BeforeEach
    public void setupCalculator() {
        calculator = new Vincenty();
    }
    
    // big identity tests

    @Test
    @DisplayName("bscheidt: distance to self should be zero")
    public void testDistanceToSelf() {
        assertEquals( 0L, calculator.between(origin, origin, big) );
        assertEquals( 0L, calculator.between(origin, origin, small) );
    }

    @Test
    @DisplayName("bscheidt: distance to same place should be zero")
    public void testDistanceToSamePlace() {
        assertEquals( 0L, calculator.between(e180, w180, big) );
        assertEquals( 0L, calculator.between(e180, w180, small) );
    }

    // antipodal tests

    @Test
    @DisplayName("bscheidt: distance origin to e180")
    public void testDistanceOriginToE180() {
        assertEquals( piBig, calculator.between(origin, e180, big) );
        assertEquals( piSmall, calculator.between(origin, e180, small) );
    }

    @Test
    @DisplayName("bscheidt: distance origin to w180")
    public void testDistanceOriginToW180() {
        assertEquals( piBig, calculator.between(origin, w180, big) );
        assertEquals( piSmall, calculator.between(origin, w180, small) );
    }

    @Test
    @DisplayName("bscheidt: distance s90 to n90")
    public void testDistanceS90ToN90() {
        assertEquals( piBig, calculator.between(s90, n90, big) );
        assertEquals( piSmall, calculator.between(s90, n90, small) );
    }

    @Test
    @DisplayName("bscheidt: distance across poles origin to n90 to e180")
    public void testDistanceAcrossPolesOriginToE180() {
        Long originToN90Big = calculator.between(origin, n90, big);
        Long originToN90Small = calculator.between(origin, n90, small);

        assertEquals( piBig, originToN90Big + calculator.between(n90, e180, big) );
        assertEquals( piSmall + 1, originToN90Small + calculator.between(n90, e180, small) );
    }

    // crossing the meridian and equator

    @Test
    @DisplayName("bscheidt: distance e45 to w45")
    public void testDistanceE45ToW45() {
        assertEquals( piBigHalf, calculator.between(e45, w45, big) );
        assertEquals( piSmallHalf, calculator.between(e45, w45, small) );
    }

    @Test
    @DisplayName("bscheidt: distance w45 to e45")
    public void testDistanceW45ToE45() {
        assertEquals( piBigHalf, calculator.between(w45, e45, big) );
        assertEquals( piSmallHalf, calculator.between(w45, e45, small) );
    }

    @Test
    @DisplayName("bscheidt: distance n45 to s45")
    public void testDistanceN45ToS45() {
        assertEquals( piBigHalf, calculator.between(n45, s45, big) );
        assertEquals( piSmallHalf, calculator.between(n45, s45, small) );
    }

    @Test
    @DisplayName("bscheidt: distance s45 to n45")
    public void testDistanceS45ToN45() {
        assertEquals( piBigHalf, calculator.between(s45, n45, big) );
        assertEquals( piSmallHalf, calculator.between(s45, n45, small) );
    }

    // test combinational distances

    @Test
    @DisplayName("bscheidt: crossing the entire circumference with big radius origin to n90 to e90 to e180 to s90")
    public void testDistanceCircumferenceInQuartersBig() {
        Long distanceOne = calculator.between(origin, n90, big);
        Long distanceTwo = calculator.between(n90, e90, big);
        Long distanceThree = calculator.between(e90, e180, big);
        Long distanceFour = calculator.between(e180, s90, big);

        assertEquals( piBigHalf * 4, distanceOne + distanceTwo + distanceThree + distanceFour );
    }

    @Test
    @DisplayName("bscheidt: crossing the entire circumference with small radius origin to n90 to e90 to e180 to s90")
    public void testDistanceCircumferenceInQuartersSmall() {
        Long distanceOne = calculator.between(origin, n90, small);
        Long distanceTwo = calculator.between(n90, e90, small);
        Long distanceThree = calculator.between(e90, e180, small);
        Long distanceFour = calculator.between(e180, s90, small);

        assertEquals( piSmallHalf * 4, distanceOne + distanceTwo + distanceThree + distanceFour );
    }

    // test realistic distances
    // values tested against Google distances

    @Test
    @DisplayName("bscheidt: test Paris to New York")
    public void testDistanceParisToNewYork() {
        Geo newYork = new Geo(40.730610, -73.935242);
        Geo paris = new Geo(48.864716, 2.349014);

        assertEquals( 5831, calculator.between(newYork, paris, earthRadiusKM) );
        assertEquals( 3623, calculator.between(newYork, paris, earthRadiusMi) );
        assertEquals( 3152, calculator.between(newYork, paris, earthRadiusNM) );
    }

}
