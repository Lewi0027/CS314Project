package com.tco.query;

import com.tco.requests.Place;

public class Select {

    private final static String TABLE = "world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id";
    private final static String[] COLUMN = {"world.name", "world.id", "world.municipality", "region.name", "country.name"};
    private final static String COLUMNS = " world.id AS id, world.name, world.municipality, region.name AS region, country.name AS country, world.latitude, world.longitude, world.altitude, world.type";

    static String near(Boundary boundary, Place place) {
        String where = "WHERE latitude BETWEEN " + boundary.latMin + " AND " + boundary.latMax + 
        " AND longitude BETWEEN " + boundary.lonMin + " AND " + boundary.lonMax;

        return statementNear(where, COLUMNS, "LIMIT " + 200, place);
    }

    static String found(String match) {
        String where = createWhereString(match);
        return statementFind(where, "COUNT(*) AS count", "");
    }

    static String match(String match, int limit) {
        if(limit > 100) limit = 100;
        String where = createWhereString(match);
        return statementFind(where, COLUMNS + " ", "LIMIT " + limit);
    }

    static String createWhereString(String match) {
        String where = "WHERE ";
        for(int i = 0; i < COLUMN.length; i++) {
            if(i != COLUMN.length - 1) {
                where += COLUMN[i] +" LIKE \"%" + match + "%\"" + " OR ";
            }
            else {
                where += COLUMN[i] +" LIKE \"%" + match + "%\"";
            }   
        }
        return where;
    }

    static String statementFind(String where, String data, String limit) {
        return "SELECT "
            + data
            + " FROM " + TABLE
            + " " + where + " "
            + limit + ";";
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
