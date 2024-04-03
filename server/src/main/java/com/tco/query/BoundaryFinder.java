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
        double latDegreePerDistance = calculateLatRatio();
        this.boundary.latMin = getLatMin(latDegreePerDistance);
        this.boundary.latMax = getLatMax(latDegreePerDistance);

        double latBoundaryByEquator = Math.min(Math.abs(this.boundary.latMin), Math.abs(this.boundary.latMax));

        this.boundary.lonMin = getLonMin(latBoundaryByEquator);
        this.boundary.lonMax = getLonMax(latBoundaryByEquator);
    }

    private double calculateLatRatio() {
        return -1;
    }

    private double getLatMin(double ratio) {
        return -1;
    }

    private double getLatMax(double ratio) {
        return -1;
    }

    private double getLonMin(double latByEquator) {
        return -1;
    }

    private double getLonMax(double latByEquator) {
        return -1;
    }

    private double degreesOfLongitude(double latByEquator) {
        return -1;
    }

    public Boundary getBoundary() {
        return this.boundary;
    }
}
