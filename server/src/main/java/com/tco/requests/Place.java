package com.tco.requests;

import com.tco.misc.GeographicCoordinate;
import java.util.LinkedHashMap;

public class Place extends LinkedHashMap<String, String> implements GeographicCoordinate{

    public Place(String latitude, String longitude) {
        this.put("latitude", latitude);
        this.put("longitude", longitude);
    }

    public Place() {}

    @Override
    public double latRadians() {
        return 0;
    }
    @Override
    public double lonRadians() {
        return 0;
    }
}
