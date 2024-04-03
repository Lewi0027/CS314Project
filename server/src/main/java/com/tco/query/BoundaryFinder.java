package com.tco.query;

public class BoundaryFinder {
    double lat;
    double lon;
    double earthRadius;
    int distance;
    Boundary boundary;
    boolean boundaryCrossesPole;

    public BoundaryFinder(double lat, double lon, double earthRadius, int distance) {
        this.lat = lat;
        this.lon = lon;
        this.earthRadius = earthRadius;
        this.distance = distance + 1; // +1 helps with rounding errors and edge cases
        this.boundaryCrossesPole = false;
        this.boundary = new Boundary();
        calculateBoundary();
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
