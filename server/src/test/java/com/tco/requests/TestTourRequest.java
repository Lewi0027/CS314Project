package com.tco.requests;

import com.tco.misc.BadRequestException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class TestTourRequest {
    TourRequest request;
    Places places;

    @BeforeEach
    public void beforeEach() {
        places = new Places();
    }

    @Test
    @DisplayName("bscheidt: test request empty places")
    public void testEmptyPlaces() {
        request = new TourRequest(places, 1000.0, "vincenty", 1.0);
        Places testPlaces = new Places();
        
        request.buildResponse();

        assertEquals(testPlaces, request.getPlaces());
    }

    @Test
    @DisplayName("bscheidt: test request 1 place")
    public void testOnePlace() {
        places.add(new Place("0", "0"));
        request = new TourRequest(places, 1000.0, "vincenty", 1.0);

        Places testPlaces = new Places();
        testPlaces.add(new Place("0", "0"));

        request.buildResponse();

        assertEquals(testPlaces, request.getPlaces());
    }

    @Test
    @DisplayName("bscheidt: test request multiple places")
    public void testMultiplePlaces() {
        // E <--------50--------> C <-1-> B <--2--> A <----10----> D
        // a 0.6 degree difference in coordinates corresponds to 1 unit distance difference when earthRadius is 100

        places.add(new Place("0", "0"));
        places.add(new Place("0", "-1.2"));
        places.add(new Place("0", "-1.8"));
        places.add(new Place("0", "6"));
        places.add(new Place("0", "-31.8"));
        request = new TourRequest(places, 100.0, "vincenty", 1.0);

        Places testPlaces = new Places();
        testPlaces.add(new Place("0", "-1.8"));
        testPlaces.add(new Place("0", "-1.2"));
        testPlaces.add(new Place("0", "0"));
        testPlaces.add(new Place("0", "6"));
        testPlaces.add(new Place("0", "-31.8"));

        request.buildResponse();

        assertEquals(testPlaces, request.getPlaces());
    }


}
