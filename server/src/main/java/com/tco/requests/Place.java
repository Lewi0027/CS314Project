package com.tco.requests;

import com.tco.misc.GeographicCoordinate;

public class Place implements GeographicCoordinate{
    private double latitude;
    private double longitude;

    public Place(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
