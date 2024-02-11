package com.tco.misc;

import static java.lang.Math.*;

public class Vincenty implements GreatCircleDistance{

    @Override
    public Long between(GeographicCoordinate from, GeographicCoordinate to, double earthRadius) {
        Double latitudeFrom = from.latRadians();
        Double longitudeFrom = from.lonRadians();
        Double latitudeTo = to.latRadians();
        Double longitudeTo = to.lonRadians();

        Double deltaLongitude = longitudeTo - longitudeFrom;

        Double numerator = calculateNumerator(latitudeFrom, latitudeTo, deltaLongitude);

        Double denominator = calculateDenominator(latitudeFrom, latitudeTo, deltaLongitude);

        Long distance = calculateDistance(numerator, denominator, earthRadius);

        return distance;
    }

    protected Double calculateNumerator(Double latitudeFrom, Double latitudeTo, Double deltaLongitude) {
        Double termOne = cos(latitudeTo) * sin(deltaLongitude);
        termOne = pow(termOne, 2);

        Double termTwo = cos(latitudeFrom) * sin(latitudeTo) - sin(latitudeFrom) * cos(latitudeTo) * cos(deltaLongitude);
        termTwo = pow(termTwo, 2);

        return sqrt(termOne + termTwo);

    }

    protected Double calculateDenominator(Double latitudeFrom, Double latitudeTo, Double deltaLongitude) {
        return sin(latitudeFrom) * sin(latitudeTo) + cos(latitudeFrom) * cos(latitudeTo) * cos(deltaLongitude);
    }

    protected Long calculateDistance(Double numerator, Double denominator, Double earthRadius) {
        Double centralAngle = atan2(numerator, denominator);
        Double distance = centralAngle * earthRadius;

        return (long) round(distance);
    }
}
