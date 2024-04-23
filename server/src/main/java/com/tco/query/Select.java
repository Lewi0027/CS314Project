package com.tco.query;

import com.tco.requests.Place;

import java.util.List;
import java.util.ArrayList;

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

    static String foundType(String match, List<String> type) {
        String where = createWhereString(match);
        return statementType(where, "COUNT(*) AS count", "", type);
    }

    static String match(String match, int limit) {
        if(limit > 100) limit = 100;
        String where = createWhereString(match);
        return statementFind(where, COLUMNS + " ", "LIMIT " + limit);
    }

    static String type(String match, int limit, List<String> type) {
        if(limit > 100) limit = 100;
        String where = createWhereString(match);
        return statementType(where, COLUMNS + " ", "LIMIT " + limit, type);
    }

    static String createWhereString(String match) {
        String where = "WHERE (";
        for(int i = 0; i < COLUMN.length; i++) {
            if(i != COLUMN.length - 1) {
                where += COLUMN[i] +" LIKE \"%" + match + "%\"" + " OR ";
            }
            else {
                where += COLUMN[i] +" LIKE \"%" + match + "%\"";
            }   
        }
        where += ")";
        return where;
    }

    static String statementFind(String where, String data, String limit) {
        return "SELECT "
            + data
            + " FROM " + TABLE
            + " " + where + " "
            + limit + ";";
    }

    static String statementWhere(String where1, String data, String limit, List<String> where2) {
        return "SELECT " 
            + data
            + " FROM " + TABLE
            + " " + where1 + " "
            + generateWhereString(where2)
            + limit + ";";
    }

    static String statementType(String where, String data, String limit, List<String> type) {
        return "SELECT "
            + data
            + " FROM " + TABLE
            + " " + where + " "
            + generateTypeString(type)
            + limit + ";";
    }

    static String generateTypeString(List<String> type) {
        String typeString = " AND (";
        for(int i = 0; i < type.size(); i++) {
            String port = type.get(i);
            if(i == type.size() - 1) {
                typeString += "world.type LIKE \"%" + port + "%\"";
            }
            else typeString += "world.type LIKE \"%" + port + "%\" OR ";
        }
        typeString += ") ";
        return typeString;
    }

    static String generateWhereString(List<String> where) {
        String typeString = " AND (";
        for(int i = 0; i < where.size(); i++) {
            String port = where.get(i);
            if(i == where.size() - 1) {
                typeString += "(region.name LIKE \"%" + port + "%\" OR country.name LIKE \"%" + port + "%\")";
            }
            else typeString += "(region.name LIKE \"%" + port + "%\" OR country.name LIKE \"%" + port + "%\") OR ";
        }
        typeString += ") ";
        return typeString;
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
