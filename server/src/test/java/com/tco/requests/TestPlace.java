package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

public class TestPlace {

    private Place place;

    private Random random = new Random();
    
    @Test
    @DisplayName("bscheidt: latRadians() returns correct conversion of 0")
    public void testLatRadiansZero() {
        place = new Place("0", "0");
        assertEquals((0 * (Math.PI/180)), place.latRadians());
    }
    
    @Test
    @DisplayName("bscheidt: latRadians() returns correct conversion of 90")
    public void testLatRadiansMax() {
        place = new Place("90", "0");
        assertEquals((90 * (Math.PI/180)), place.latRadians());
    }
    
    @Test
    @DisplayName("bscheidt: latRadians() returns correct conversion of -90")
    public void testLatRadiansMin() {
        place = new Place("-90", "0");
        assertEquals((-90 * (Math.PI/180)), place.latRadians());
    }
    
    @Test
    @DisplayName("bscheidt: latRadians() returns correct conversion of random latitude")
    public void testLatRadiansRandom() {
        double randomDouble = random.nextDouble() * 180 - 90; 
        String testValue = randomDouble + "";
        place = new Place(testValue, "0");
        assertEquals((randomDouble * (Math.PI/180)), place.latRadians());
    }

    @Test
    @DisplayName("ajlei: lonRadians() returns correct conversion of 0")
    public void testLonRadiansZero() {
        place = new Place("0", "0");
        assertEquals((0 * (Math.PI/180)), place.lonRadians());
    }

    @Test
    @DisplayName("ajlei: lonRadians() returns correct conversion of 180")
    public void testLonRadiansMax() {
        place = new Place("0", "180");
        assertEquals((180 * (Math.PI/180)), place.lonRadians());
    }

    @Test
    @DisplayName("ajlei: lonRadians() returns correct conversion of -180")
    public void testLonRadiansMin() {
        place = new Place("0", "-180");
        assertEquals((-180 * (Math.PI/180)), place.lonRadians());
    }

    @Test
    @DisplayName("ajlei: lonRadians() returns correct conversion of random longitude")
    public void testLonRadiansRandom() {
        double randomDouble = random.nextDouble() * 360 - 180; 
        String testValue = randomDouble + "";
        place = new Place("0", testValue);
        assertEquals((randomDouble * (Math.PI/180)), place.lonRadians());
    }
}
