package com.tco.query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tco.requests.Place;

public class TestNearLocations {
    NearLocations locations;

    @BeforeEach
    public void setup() {
        Place foco = new Place("40.572978", "-105.086761");
        locations = new NearLocations(foco, 5, 3959, 10, "vincenty");
    }

    @Test
    @DisplayName("bscheidt: test getting 5 mile boundary from CSU")
    public void testGetBoundaryFoco() {
        Boundary boundary = locations.getSearchBoundary();

        double expectedLatMin = 40.486144285153195;
        double expectedLatMax = 40.6598117148468;
        double expectedLonMin = -105.20093131209354;
        double expectedLonMax = -104.97259068790645;
        
        assertEquals(expectedLatMin, boundary.latMin);
        assertEquals(expectedLatMax, boundary.latMax);
        assertEquals(expectedLonMin, boundary.lonMin);
        assertEquals(expectedLonMax, boundary.lonMax);
    }

    @Test
    @DisplayName("bscheidt: test one degree change each direction")
    public void testGetBoundaryOneDegree() {
        Place origin = new Place("0", "0");
        locations = new NearLocations(origin, 69, 3959, 10, "vincenty");

        Boundary boundary = locations.getSearchBoundary();

        double expectedLatMin = -1.0;
        double expectedLatMax = 1.0;
        double expectedLonMin = -1.0;
        double expectedLonMax = 1.0;

        // round to nearest whole number
        double actualLatMin = Math.ceil(boundary.latMin * 10) / 10;
        double actualLatMax = Math.floor(boundary.latMax * 10) / 10;
        double actualLonMin = Math.ceil(boundary.lonMin * 10) / 10;
        double actualLonMax = Math.floor(boundary.lonMax * 10) / 10;
        
        assertEquals(expectedLatMin, actualLatMin);
        assertEquals(expectedLatMax, actualLatMax);
        assertEquals(expectedLonMin, actualLonMin);
        assertEquals(expectedLonMax, actualLonMax);
    }

    @Test
    @DisplayName("bscheidt: test crossing pole has all 360 degrees for longitude")
    public void testGetBoundaryPoleCross() {
        Place northPole = new Place("90", "0");
        Place southPole = new Place("90", "0");
        NearLocations locationsNorth = new NearLocations(northPole, 5, 1000, 10, "vincenty");
        NearLocations locationsSouth = new NearLocations(southPole, 5, 1000, 10, "vincenty");

        Boundary boundaryNorth = locationsNorth.getSearchBoundary();
        Boundary boundarySouth = locationsSouth.getSearchBoundary();

        double expectedLonMin = -180.0;
        double expectedLonMax = 180.0;

        assertEquals(expectedLonMin, boundaryNorth.lonMin);
        assertEquals(expectedLonMax, boundaryNorth.lonMax);

        assertEquals(expectedLonMin, boundarySouth.lonMin);
        assertEquals(expectedLonMax, boundarySouth.lonMax);
    }
}
