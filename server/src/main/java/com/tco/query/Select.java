package com.tco.query;

import com.tco.requests.Place;

public class Select {

    private final static String TABLE = "world";
    private final static String COLUMN = "name";
    private final static String COLUMNS = "id,name,municipality,iso_region,iso_country,latitude,longitude,altitude,type";

    static String near(Boundary boundary, Place place) {
        return "";
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
        return "";
    }
}
