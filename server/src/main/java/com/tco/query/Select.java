package com.tco.query;

import com.tco.requests.Place;

public class Select {

    private final static String TABLE = "world";
    private final static String COLUMN = "name";
    private final static String COLUMNS = "id,name,municipality,iso_region,iso_country,latitude,longitude,altitude,type";

    static String near(Boundary boundary, Place place) {
        String where = "WHERE latitude BETWEEN " + boundary.latMin + " AND " + boundary.latMax + 
        " AND longitude BETWEEN " + boundary.lonMin + " AND " + boundary.lonMax;

        return statementNear(where, COLUMNS, "LIMIT " + 200, place);
    }

    static String found(String match) {
        return "";
    }

    static String match(String match, int limit) {
        return "";
    }

    static String statementFind(String where, String data, String limit) {
        return "";
    }

    static String statementNear(String where, String data, String limit, Place place) {
        return "SELECT "
            + data
            + " FROM " + TABLE + " "
            + where
            + " ORDER BY ABS(latitude - " + place.get("latitude") + ") + ABS(longitude - " + place.get("longitude") + ") "
            + limit
            + ";";
    }
}
