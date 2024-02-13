package com.tco.requests;

import com.tco.misc.BadRequestException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class TestDistancesRequest {

    DistancesRequest request;
    Places places;
    Distances distances;

    // to test minimum earth radius value
    final static Double smallRadius = 1.0;
    final static long smallPi = Math.round(Math.PI * smallRadius);
    final static long smallPiHalf = Math.round(Math.PI / 2.0 * smallRadius);

    // to test large values with significant digits to verify double/long
    static final Double bigRadius = 100000000000000.0;
    static final long bigPi = Math.round(Math.PI * bigRadius);
    static final long bigPiHalf = Math.round(Math.PI / 2.0 * bigRadius);

    @BeforeEach
    public void beforeEach() {
        places = new Places();
        distances = new Distances();
    }


    // bad request testing

    @Test
    @DisplayName("bscheidt: latitude too low")
    public void testLatitudeLow() {
        places.add(new Place("-91", "0"));
        request = new DistancesRequest(places, bigRadius, null);
        assertThrows(BadRequestException.class, () -> request.buildResponse());
    }

    @Test
    @DisplayName("bscheidt: latitude too high")
    public void testLatitudeHigh() {
        places.add(new Place("91", "0"));
        request = new DistancesRequest(places, bigRadius, null);
        assertThrows(BadRequestException.class, () -> request.buildResponse());
    }

    @Test
    @DisplayName("bscheidt: longitude too low")
    public void testLongitudeLow() {
        places.add(new Place("0", "-181"));
        request = new DistancesRequest(places, bigRadius, null);
        assertThrows(BadRequestException.class, () -> request.buildResponse());
    }

    @Test
    @DisplayName("bscheidt: longitude too high")
    public void testLongitudeHigh() {
        places.add(new Place("0", "181"));
        request = new DistancesRequest(places, bigRadius, null);
        assertThrows(BadRequestException.class, () -> request.buildResponse());
    }

    @Test
    @DisplayName("bscheidt: earth radius null")
    public void testEarthRadiusNull() {
        request = new DistancesRequest(places, null, null);
        assertThrows(BadRequestException.class, () -> request.buildResponse());
    }

    @Test
    @DisplayName("bscheidt: earth radius negative")
    public void testEarthRadiusNegative() {
        request = new DistancesRequest(places, -1.0, null);
        assertThrows(BadRequestException.class, () -> request.buildResponse());
    }

    @Test
    @DisplayName("bscheidt: places null")
    public void testPlacesNull() {
        request = new DistancesRequest(null, bigRadius, null);
        assertThrows(BadRequestException.class, () -> request.buildResponse());
    }

}
