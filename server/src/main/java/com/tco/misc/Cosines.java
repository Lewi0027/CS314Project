package com.tco.misc;

import static java.lang.Math.*;

public class Cosines implements GreatCircleDistance{

    @Override
    public Long between(GeographicCoordinate from, GeographicCoordinate to, double earthRadius) {
        Double latitudeFrom = from.latRadians();
        Double longitudeFrom = from.lonRadians();
        Double latitudeTo = to.latRadians();
        Double longitudeTo = to.lonRadians();

        Double calculatedCosine = cosines(latitudeFrom, longitudeFrom, latitudeTo, longitudeTo);

        double distance = calculatedCosine * earthRadius;

        return Math.round(distance);
    }

    private static double cosines(Double lat1, Double lon1, Double lat2, Double lon2){
        Double longitudeDifference = lon2 - lon1;
        Double sinProduct = Math.sin(lat1) * Math.sin(lat2);
        Double cosProduct = Math.cos(lat1) * Math.cos(lat2) * Math.cos(longitudeDifference);
        Double radianDistance = Math.acos(sinProduct + cosProduct);
        return radianDistance;
        
    }
}
