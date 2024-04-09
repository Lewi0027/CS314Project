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

    protected void calculateBoundary() {
        double latDegreePerDistance = calculateLatRatio();
        this.boundary.latMin = getLatMin(latDegreePerDistance);
        this.boundary.latMax = getLatMax(latDegreePerDistance);

        double latBoundaryByEquator = Math.min(Math.abs(this.boundary.latMin), Math.abs(this.boundary.latMax));

        this.boundary.lonMin = getLonMin(latBoundaryByEquator);
        this.boundary.lonMax = getLonMax(latBoundaryByEquator);
    }

    protected double calculateLatRatio() {
        double circumference = (2 * Math.PI) * this.earthRadius;
        return circumference / 360;
    }

    protected double getLatMin(double ratio) {
        return getLatMinMax(ratio, true);
    }

    protected double getLatMax(double ratio) {
        return getLatMinMax(ratio, false);
    }
    
    protected double getLatMinMax(double ratio, boolean valIsMin ) {
        double lat;
	    if (valIsMin) {
            lat = this.lat - (this.distance / ratio);
        }
        else {
            lat = this.lat + (this.distance / ratio);
        }
        if (lat < -90 || lat > 90) {
            this.boundaryCrossesPole = true;
            lat = (lat < -90) ? -90 : 90;
        }
        return lat;
    }

    protected double getLonMin(double latByEquator) {
        if(this.boundaryCrossesPole) return -180;
        return this.lon - degreesOfLongitude(latByEquator);
    }

    protected double getLonMax(double latByEquator) {
        if(this.boundaryCrossesPole) return 180;
        return this.lon + degreesOfLongitude(latByEquator);

    }

    protected double degreesOfLongitude(double latByEquator) {
        double radiusAtLatitude = this.earthRadius * Math.cos(Math.toRadians(latByEquator));
        double distancePerDegree = (2 * Math.PI * radiusAtLatitude) / 360;
        return this.distance / distancePerDegree;
    }

    public Boundary getBoundary() {
        return this.boundary;
    }

    // testing
    protected void setCrossesPoleToFalse() {
        this.boundaryCrossesPole = false;
    }
    protected void setCrossesPoleToTrue() {
        this.boundaryCrossesPole = true;
    }
}
