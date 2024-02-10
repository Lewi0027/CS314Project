package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPlace {

    private Place place;
    
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
        place = new Place("37.493352", "0");
        assertEquals((37.493352 * (Math.PI/180)), place.latRadians());
    }
}
