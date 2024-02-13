package com.tco.misc;

import static java.lang.Math.*;

public class Haversine implements GreatCircleDistance{
    
    @Override
    public Long between(GeographicCoordinate from, GeographicCoordinate to, double earthRadius) {
        Double latitudeFrom = from.latRadians();
        Double longitudeFrom = from.lonRadians();
        Double latitudeTo = to.latRadians();
        Double longitudeTo = to.lonRadians();

        Double latitudeDifference = latitudeTo - latitudeFrom;
        Double longitudeDifference = longitudeTo - longitudeFrom;

        Double firstEquation = haversineMethod(latitudeDifference) + haversineMethod(longitudeDifference) * cosineLatitudes(latitudeFrom, latitudeTo);
        Double finalEquation = 2 * Math.asin(Math.sqrt(firstEquation));

        double distance = finalEquation * earthRadius;

        return Math.round(distance);
    }

    protected Double haversineMethod (Double differenceInDistance) {
        return (Math.pow(Math.sin(differenceInDistance/2), 2));
    }

    protected Double cosineLatitudes(Double latitudeOne, Double latitudeTwo) {
        return (Math.cos(latitudeOne) * Math.cos(latitudeTwo));
    }

}
