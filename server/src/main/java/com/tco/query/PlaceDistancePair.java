package com.tco.query;

import com.tco.requests.Place;

public class PlaceDistancePair {
    private Place place;
    private long distance;

    public PlaceDistancePair(Place place, long distance) {
        this.place = place;
        this.distance = distance;
    }

    public Place getPlace() {
        return this.place;
    }

    public long getDistance() {
        return this.distance;
    }

}