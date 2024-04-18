package com.tco.requests;

import com.tco.misc.BadRequestException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class TestNearRequest {

    NearRequest request;
    Place place;

    @BeforeEach
    public void beforeEach() {
        place = new Place();
    }
    
    @Test
    @DisplayName("ajlei: test formula not in formulae")
    public void testWithInvalidFormula() {
        place = new Place("0", "0");
        request = new NearRequest(place, 1, 1000.0, 10, "elipses");

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            request.buildResponse();
        });
    }

    @Test
    @DisplayName("ajlei: test request expected formula")
    public void testWithValidFormula() {
        place = new Place("0", "0");
        request = new NearRequest(place, 1, 1000.0, 10, "vincenty");

        try {
            request.buildResponse();
        }
        catch (BadRequestException e) {
            fail("Did not expect BadRequestException to be thrown.");
        }
    }

}
