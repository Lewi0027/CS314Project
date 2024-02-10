package com.tco.requests;

import com.tco.misc.GeographicCoordinate;
import java.util.LinkedHashMap;

public class Place extends LinkedHashMap<String, String> implements GeographicCoordinate {

    public Place(String latitude, String longitude) {
        this.put("latitude", latitude);
        this.put("longitude", longitude);
    }

    public Place() {}

    @Override
    public Double latRadians() {
        String latitude = this.get("latitude");
        Double latitudeDecimal = Double.parseDouble(latitude);
        return Math.toRadians(latitudeDecimal);
    }
    @Override
    public Double lonRadians() {
        return 0.0;
    }
}
