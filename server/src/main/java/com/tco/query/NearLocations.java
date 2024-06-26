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

    protected Places findPlacesWithinBoundary(Boundary searchBoundary) throws Exception {
        Places allFound;

        if(searchBoundary.lonMax > 180 || searchBoundary.lonMin < -180) {
            allFound = combineQueries(searchBoundary);
        } else {
            allFound = Database.places(Select.near(searchBoundary, this.place));
        }

        return allFound;
    }

    protected void buildDistancesAndPlaces(Places allFound) {
        List<PlaceDistancePair> pairs = new ArrayList<>();

        populatePairs(allFound, pairs);
        sortAndTrimPairs(pairs);
        fillPlacesAndDistances(pairs);
    }

    protected Places combineQueries(Boundary searchBoundary) throws Exception {
        Boundary one = new Boundary(searchBoundary);
        Boundary two = new Boundary(searchBoundary);

        modifyCombinedBoundaries(one, two, searchBoundary);

        return queryCombinedBoundaries(one, two);
    }

    protected Places queryCombinedBoundaries(Boundary one, Boundary two) throws Exception{
        Places queryOne = Database.places(Select.near(one, this.place));
        Places queryTwo = Database.places(Select.near(two, this.place));

        queryOne.addAll(queryTwo);

        return queryOne;
    }

    protected void modifyCombinedBoundaries(Boundary one, Boundary two, Boundary searchBoundary) {
        if (searchBoundary.lonMax > 180) {
            one.lonMax = 180;
            two.lonMin = -180;
            two.lonMax = -180 + (searchBoundary.lonMax - 180); 
        }
        else {
            one.lonMin = -180;
            two.lonMin = 180 + (searchBoundary.lonMin + 180);
            two.lonMax = 180;
        }
    }

    protected void populatePairs(Places allFound, List<PlaceDistancePair> pairs) {
        GreatCircleDistance distanceCalculator = FormulaFactory.createFormula(this.formula);

        for (int index = 0; index < allFound.size(); index++) {
            GeographicCoordinate from = this.place;
            GeographicCoordinate to = allFound.get(index);

            long distance = distanceCalculator.between(from, to, this.earthRadius);

            if (distance <= this.distance) {
                pairs.add(new PlaceDistancePair(allFound.get(index), distance));
            }
        }
    }

    protected void fillPlacesAndDistances(List<PlaceDistancePair> pairs) {
        this.places.clear();
        this.distances.clear();

        for(PlaceDistancePair pair : pairs) {
            this.places.add(pair.getPlace());
            this.distances.add(pair.getDistance());
        }
        
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


    // For testing
    protected Places getPlaces() { return this.places; }

    protected void setPlaces(Places cover) { this.places = cover; }
    protected void setDistances(Distances cover) { this.distances = cover; }

}