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

}
