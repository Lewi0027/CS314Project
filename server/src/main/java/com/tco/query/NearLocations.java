package com.tco.query;

import com.tco.requests.Places;
import com.tco.requests.Place;
import com.tco.requests.Distances;

import com.tco.misc.GreatCircleDistance;
import com.tco.misc.FormulaFactory;
import com.tco.misc.GeographicCoordinate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Comparator;

import java.lang.Exception;

public class NearLocations {

    private Places places;
    private Distances distances;
    private int distance;
    private Place place;
    private int limit;
    private double earthRadius;
    private String formula;
    private Boundary searchBoundary;

    public NearLocations(Place place, int distance, double earthRadius, int limit, String formula) {
        // Constructor
    }

    public Distances distances() {
        return null;
    }

    public Places near() throws Exception {
        return null;
    }

    private Boundary getSearchBoundary() {
        return null;
    }

    private Places findPlacesWithinBoundary(Boundary searchBoundary) throws Exception {
        return null;
    }

    private void buildDistancesAndPlaces(Places allFound) {

    }

    private Places combineQueries(Boundary searchBoundary) throws Exception {
        return null;
    }

    private void modifyCombinedBoundaries(Boundary one, Boundary two, Boundary searchBoundary) {

    }

    private void populatePairs(Places allFound, List<PlaceDistancePair> pairs) {

    }

    private void fillPlacesAndDistances(List<PlaceDistancePair> pairs) {

    }

    private void sortAndTrimPairs(List<PlaceDistancePair> pairs) {

    }

}