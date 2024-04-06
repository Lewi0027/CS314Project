package com.tco.query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.ArrayList;

import com.tco.requests.Place;
import com.tco.requests.Places;
import com.tco.requests.Distances;

public class TestNearLocations {
    NearLocations locations;
    Place foco;
    Place loveland;
    Place windsor;
    Place greeley;
    Place estesPark;
    Place boulder;

    @BeforeEach
    public void setup() {
        foco = new Place("40.572978", "-105.086761");
        loveland = new Place("40.40", "-105.08"); // distance from foco: 12
        windsor = new Place("40.46", "-104.89"); // distance from foco: 13
        greeley = new Place("40.42", "-104.71"); // distance from foco: 22
        estesPark = new Place("40.38", "-105.52"); // distance from foco: 26
        boulder = new Place("40.02", "-105.27"); // distance from foco: 39

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

    @Test
    @DisplayName("bscheidt: test sorting place/distance pairs")
    public void testSortPDPairs() {
        Place placeOne = new Place("0", "0");
        Place placeTwo = new Place("20", "20");
        Place placeThree = new Place("40", "40");
        Place placeFour = new Place("60", "60");

        long distanceOne = 40l;
        long distanceTwo = 30l;
        long distanceThree = 20l;
        long distanceFour = 10l;

        PlaceDistancePair one = new PlaceDistancePair(placeOne, distanceOne);
        PlaceDistancePair two = new PlaceDistancePair(placeTwo, distanceTwo);
        PlaceDistancePair three = new PlaceDistancePair(placeThree, distanceThree);
        PlaceDistancePair four = new PlaceDistancePair(placeFour, distanceFour);

        List<PlaceDistancePair> pairs = new ArrayList<>();

        pairs.add(one);
        pairs.add(two);
        pairs.add(three);
        pairs.add(four);

        locations.sortAndTrimPairs(pairs);

        assertEquals(four, pairs.get(0));
        assertEquals(three, pairs.get(1));
        assertEquals(two, pairs.get(2));
        assertEquals(one, pairs.get(3));

    }

    @Test
    @DisplayName("bscheidt: test trimming place/distance pairs")
    public void testTrimPDPairs() {
        Place placeOne = new Place("0", "0");
        Place placeTwo = new Place("20", "20");
        Place placeThree = new Place("40", "40");
        Place placeFour = new Place("60", "60");

        long distanceOne = 40l;
        long distanceTwo = 30l;
        long distanceThree = 20l;
        long distanceFour = 10l;

        PlaceDistancePair one = new PlaceDistancePair(placeOne, distanceOne);
        PlaceDistancePair two = new PlaceDistancePair(placeTwo, distanceTwo);
        PlaceDistancePair three = new PlaceDistancePair(placeThree, distanceThree);
        PlaceDistancePair four = new PlaceDistancePair(placeFour, distanceFour);

        List<PlaceDistancePair> pairs = new ArrayList<>();

        pairs.add(one);
        pairs.add(two);
        pairs.add(three);
        pairs.add(four);

        locations = new NearLocations(placeOne, 0, 0, 1, "vincenty");

        locations.sortAndTrimPairs(pairs);

        assertEquals(1, pairs.size());
        assertEquals(four, pairs.get(0));

    }

    @Test
    @DisplayName("bscheidt: test populatePairs() all within correct distance")
    public void testPopulatePairsWithinDistance() {
        Places allFound = new Places();
        allFound.add(windsor);
        allFound.add(greeley);
        allFound.add(loveland);
        allFound.add(estesPark);
        allFound.add(boulder);

        locations = new NearLocations(foco, 39, 3959, 10, "vincenty");

        List<PlaceDistancePair> pairs = new ArrayList<>();

        locations.populatePairs(allFound, pairs);

        assertEquals(5, pairs.size());

        assertEquals(windsor, pairs.get(0).getPlace());
        assertEquals(greeley, pairs.get(1).getPlace());
        assertEquals(loveland, pairs.get(2).getPlace());
        assertEquals(estesPark, pairs.get(3).getPlace());
        assertEquals(boulder, pairs.get(4).getPlace());

        assertEquals(13l, pairs.get(0).getDistance());
        assertEquals(22l, pairs.get(1).getDistance());
        assertEquals(12l, pairs.get(2).getDistance());
        assertEquals(26l, pairs.get(3).getDistance());
        assertEquals(39l, pairs.get(4).getDistance());
    }

    @Test
    @DisplayName("bscheidt: test populatePairs() only some within correct distance")
    public void testPopulatePairsWithinPartialDistance() {
        Places allFound = new Places();
        allFound.add(windsor);
        allFound.add(greeley);
        allFound.add(loveland);
        allFound.add(estesPark);
        allFound.add(boulder);

        locations = new NearLocations(foco, 21, 3959, 10, "vincenty");

        List<PlaceDistancePair> pairs = new ArrayList<>();

        locations.populatePairs(allFound, pairs);

        assertEquals(2, pairs.size());

        assertEquals(windsor, pairs.get(0).getPlace());
        assertEquals(loveland, pairs.get(1).getPlace());

        assertEquals(13l, pairs.get(0).getDistance());
        assertEquals(12l, pairs.get(1).getDistance());
    }

    @Test
    @DisplayName("ajlei: test fillPlacesAndDistances with an empty pairs variable") 
    public void testEmptyPairsFill() {
        locations = new NearLocations(foco, 21, 3959, 10, "vincenty");

        Places places = new Places();
        places.add(windsor);
        places.add(greeley);
        places.add(loveland);
        places.add(estesPark);
        places.add(boulder);

        Distances distances = new Distances();
        distances.add((long)10);
        distances.add((long)11);
        distances.add((long)12);
        distances.add((long)13);
        distances.add((long)14);

        locations.setPlaces(places);
        locations.setDistances(distances);

        List<PlaceDistancePair> pairs = new ArrayList<>();
        locations.fillPlacesAndDistances(pairs);

        assertEquals(0, locations.getPlaces().size());
        assertEquals(0, locations.distances().size());
    }

    @Test
    @DisplayName("ajlei: test fillPlacesAndDistances with empty places and distances variables") 
    public void testPlacesDistancesFill() {
        locations = new NearLocations(foco, 21, 3959, 10, "vincenty");

        Places places = new Places();
        Distances distances = new Distances();

        locations.setPlaces(places);
        locations.setDistances(distances);

        List<PlaceDistancePair> pairs = new ArrayList<>();
        pairs.add(new PlaceDistancePair(windsor, (long)10));
        pairs.add(new PlaceDistancePair(greeley, (long)10));
        pairs.add(new PlaceDistancePair(loveland, (long)10));
        pairs.add(new PlaceDistancePair(estesPark, (long)10));
        pairs.add(new PlaceDistancePair(boulder, (long)10));


        locations.fillPlacesAndDistances(pairs);

        assertEquals(5, locations.getPlaces().size());
        assertEquals(5, locations.distances().size());
    }

    @Test
    @DisplayName("lewi0027: Test modifyCombinedBoundaries for wrap around") 
    public void testModifyCombinedBoundariesWrapAround() {
        Boundary one = new Boundary(90, 100, 175, 185);
        Boundary two = new Boundary(90, 100, 175, 185);
        Boundary searchBoundary = new Boundary(90, 100, 175, 185);
        Boundary oneExpected = new Boundary(90, 100, 175, 180);
        Boundary twoExpected = new Boundary(90, 100, -180, -175);

        NearLocations nearLocations = new NearLocations(new Place("0", "0"), 0, 0, 0, "");
        nearLocations.modifyCombinedBoundaries(one, two, searchBoundary);
        
        assertTrue(one.latMax == oneExpected.latMax);
        assertTrue(one.latMin == oneExpected.latMin);
        assertTrue(one.lonMax == oneExpected.lonMax);
        assertTrue(one.lonMin == oneExpected.lonMin);
        assertTrue(two.latMax == twoExpected.latMax);
        assertTrue(two.latMin == twoExpected.latMin);
        assertTrue(two.lonMax == twoExpected.lonMax);
        assertTrue(two.lonMin == twoExpected.lonMin);
    }

    @Test
    @DisplayName("lewi0027: Test modifyCombinedBoundaries wrap around negative direction") 
    public void testModifyCombinedBoundariesWrapAroundNegativeDirection() {
        Boundary one = new Boundary(90, 100, -185, -175);
        Boundary two = new Boundary(90, 100, -185, -175);
        Boundary searchBoundary = new Boundary(90, 100, -185, -175);
        Boundary oneExpected = new Boundary(90, 100, -180, -175);
        Boundary twoExpected = new Boundary(90, 100, 175, 180);

        NearLocations nearLocations = new NearLocations(new Place("0", "0"), 0, 0, 0, "");
        nearLocations.modifyCombinedBoundaries(one, two, searchBoundary);
        
        assertTrue(one.latMax == oneExpected.latMax);
        assertTrue(one.latMin == oneExpected.latMin);
        assertTrue(one.lonMax == oneExpected.lonMax);
        assertTrue(one.lonMin == oneExpected.lonMin);
        assertTrue(two.latMax == twoExpected.latMax);
        assertTrue(two.latMin == twoExpected.latMin);
        assertTrue(two.lonMax == twoExpected.lonMax);
        assertTrue(two.lonMin == twoExpected.lonMin);
    }
}
