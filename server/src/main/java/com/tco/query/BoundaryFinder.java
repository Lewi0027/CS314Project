package com.tco.query;

public class BoundaryFinder {
    double lat;
    double lon;
    double earthRadius;
    int distance;
    Boundary boundary;
    boolean boundaryCrossesPole;

    public BoundaryFinder() {
        //Constructor
    }

    private void calculateBoundary() {

    }

    private double calculateLatRatio() {
        return -1;
    }

    private double getLatMin() {
        return -1;
    }

    private double getLatMax() {
        return -1;
    }

    private double getLonMin() {
        return -1;
    }

    private double getLonMax() {
        return -1;
    }

    private double degreesOfLongitude() {
        return -1;
    }

    public Boundary getBoundary() {
        return this.boundary;
    }
}
