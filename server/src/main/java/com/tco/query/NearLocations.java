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
        this.place = place;
        this.distance = distance;
        this.earthRadius = earthRadius;
        this.limit = limit;
        this.formula = formula;
        this.places = new Places();
        this.distances = new Distances();
    }

    public Distances distances() {
        return this.distances;
    }

    public Places near() throws Exception {
        searchBoundary = getSearchBoundary();
        Places foundInBoundary = findPlacesWithinBoundary(searchBoundary);
        buildDistancesAndPlaces(foundInBoundary);

        return this.places;
    }

    protected Boundary getSearchBoundary() {
        double lat = Double.parseDouble(this.place.get("latitude"));
        double lon = Double.parseDouble(this.place.get("longitude"));

        BoundaryFinder finder = new BoundaryFinder(lat, lon, this.earthRadius, this.distance);

        return finder.getBoundary();
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

    protected void sortAndTrimPairs(List<PlaceDistancePair> pairs) {
        // Sort pairs by distance
        pairs.sort(Comparator.comparingLong(PlaceDistancePair::getDistance));

        if(pairs.size() > this.limit) {
            List<PlaceDistancePair> trimmedList = new ArrayList<>(pairs.subList(0, this.limit));
            pairs.clear();
            pairs.addAll(trimmedList);
        }
    }

}