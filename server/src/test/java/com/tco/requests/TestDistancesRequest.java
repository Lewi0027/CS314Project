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

    // request tests for bigRadius
    @Test 
    @DisplayName("diegocel: empty places big radius")
    public void testEmptyPlacesBig(){
        try{
            request = new DistancesRequest(places, bigRadius, null);
            request.buildResponse();
            assertEquals(0, request.getDistances().size());
        } catch (BadRequestException e){
            fail("Did not expect BadRequestException to be thrown.");
        }
    }

    @Test
    @DisplayName("diegocel: one place big radius")
    public void testOnePlaceBig() {
        try {
            places.add(new Place("90", "0"));
            distances.add(0L);

            request = new DistancesRequest(places, bigRadius, null);
            request.buildResponse();
            assertTrue(distances.equals(request.getDistances()));
        } catch (BadRequestException e) {
            fail("Did not expect BadRequestException to be thrown.");
        }
    }

    @Test
    @DisplayName("diegocel: two places same place big radius")
    public void testTwoPlacesSameBig() {
        try {
            places.add(new Place("90", "0"));
            places.add(new Place("90", "0"));
            distances.add(0L);
            distances.add(0L);

            request = new DistancesRequest(places, bigRadius, null);
            request.buildResponse();
            assertTrue(distances.equals(request.getDistances()));
        } catch (BadRequestException e) {
            fail("Did not expect BadRequestException to be thrown.");
        }
    }

    @Test
    @DisplayName("diegocel: short distances are 0 big radius")
    public void testShortDistancesBig() {
        try {
            places.add(new Place("0", "0.000000000000001"));
            places.add(new Place("0", "0.0000000000000011"));
            places.add(new Place("0", "0.00000000000000111"));
            distances.add(0L);
            distances.add(0L);
            distances.add(0L);

            request = new DistancesRequest(places, bigRadius, null);
            request.buildResponse();
            assertTrue(distances.equals(request.getDistances()));
        } catch (BadRequestException e) {
            fail("Did not expect BadRequestException to be thrown.");
        }
    }

    @Test 
    @DisplayName("diegocel: long combinational distances big radius")
    public void testManyPlacesBig(){
        try{
            places.add(new Place("0", "0"));
            places.add(new Place("0", "-180"));
            distances.add(bigPi);

            places.add(new Place("90", "-180"));
            distances.add(bigPiHalf);

            places.add(new Place("0", "180"));
            distances.add(bigPiHalf);

            places.add(new Place("-90", "180"));
            distances.add(bigPiHalf);

            places.add(new Place("-90", "-180"));
            distances.add(0L);
            
            places.add(new Place("0", "-180"));
            distances.add(bigPiHalf);
            distances.add(bigPi);

            request = new DistancesRequest(places, bigRadius, null);
            request.buildResponse();
            assertTrue(distances.equals(request.getDistances()));
        } catch (BadRequestException e) {
            fail("Did not expect BadRequestException to be thrown.");
        }
    }

    // request tests for small radius
    @Test 
    @DisplayName("diegocel: empty places small radius")
    public void testEmptyPlacesSmall(){
        try{
            request = new DistancesRequest(places, smallRadius, null);
            request.buildResponse();
            assertEquals(0, request.getDistances().size());
        } catch (BadRequestException e){
            fail("Did not expect BadRequestException to be thrown.");
        }
    }

    @Test
    @DisplayName("diegocel: one place small radius")
    public void testOnePlaceSmall() {
        try {
            places.add(new Place("90", "0"));
            distances.add(0L);

            request = new DistancesRequest(places, smallRadius, null);
            request.buildResponse();
            assertTrue(distances.equals(request.getDistances()));
        } catch (BadRequestException e) {
            fail("Did not expect BadRequestException to be thrown.");
        }
    }

    @Test
    @DisplayName("diegocel: two places same place small radius")
    public void testTwoPlacesSameSmall() {
        try {
            places.add(new Place("0", "0"));
            places.add(new Place("0", "0"));
            distances.add(0L);
            distances.add(0L);

            request = new DistancesRequest(places, smallRadius, null);
            request.buildResponse();
            assertTrue(distances.equals(request.getDistances()));
        } catch (BadRequestException e) {
            fail("Did not expect BadRequestException to be thrown.");
        }
    }

    @Test
    @DisplayName("diegocel: short distances are 0 small radius")
    public void testShortDistancesSmall() {
        try {
            places.add(new Place("0", "0.000000000000001"));
            places.add(new Place("0", "0.0000000000000011"));
            places.add(new Place("0", "0.00000000000000111"));
            distances.add(0L);
            distances.add(0L);
            distances.add(0L);

            request = new DistancesRequest(places, smallRadius, null);
            request.buildResponse();
            assertTrue(distances.equals(request.getDistances()));
        } catch (BadRequestException e) {
            fail("Did not expect BadRequestException to be thrown.");
        }
    }

    @Test
    @DisplayName("diegocel: three places return to starting point small radius")
    public void testThreePlacesReturnToHomeSmall() {
        try {
            places.add(new Place("0", "0"));
            places.add(new Place("0", "180"));
            places.add(new Place("0", "0"));

            distances.add(smallPi);
            distances.add(smallPi);
            distances.add(0L);

            request = new DistancesRequest(places, smallRadius, null);
            request.buildResponse();

            assertTrue(distances.equals(request.getDistances()));
        } catch (BadRequestException e) {
            fail("Did not expect BadRequestException to be thrown.");
        }
    }

    @Test
    @DisplayName("diegocel: long combinational distances small radius")
    public void testCombinationalDistancesSmall() {
        try {
            places.add(new Place("0", "0"));
            places.add(new Place("0", "-180"));
            distances.add(smallPi);

            places.add(new Place("90", "-180"));
            distances.add(smallPiHalf);

            places.add(new Place("0", "180"));
            distances.add(smallPiHalf);

            places.add(new Place("-90", "180"));
            distances.add(smallPiHalf);

            places.add(new Place("-90", "-180"));
            distances.add(0L);

            places.add(new Place("0", "-180"));
            distances.add(smallPiHalf);
            distances.add(smallPi);

            request = new DistancesRequest(places, smallRadius, null);
            request.buildResponse();
            assertTrue(distances.equals(request.getDistances()));
        } catch (BadRequestException e) {
            fail("Did not expect BadRequestException to be thrown.");
        }
    }

    @Test
    @DisplayName("ajlei: test formula not in formulae")
    public void testWithInvalidFormula() {
        places.add(new Place("0", "0"));
        request = new DistancesRequest(places, smallRadius, "elipses");

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            request.buildResponse();
        });
    }
}
