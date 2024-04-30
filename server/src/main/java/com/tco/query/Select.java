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
        return "SELECT COUNT(*) AS count FROM " + TABLE + " " + where + " " + statementType(type) + ";";
    }

    static String foundWhere(String match, List<String> whereConditions) {
        String whereClause = createWhereString(match);
        return "SELECT COUNT(*) AS count FROM " + TABLE + " " + whereClause + " " + statementWhere(whereConditions) + ";";
    }

    static String foundWhereAndType(String match, List<String> where, List<String> type) {
        String whereClause = createWhereString(match);
        return "SELECT COUNT(*) AS count FROM " + TABLE + " " + whereClause + " " + statementWhere(where) + statementType(type) + ";";
    }

    static String match(String match, int limit) {
        if(limit > 100) limit = 100;
        String where = createWhereString(match);
        return statementFind(where, COLUMNS + " ", "LIMIT " + limit);
    }

    static String type(String match, int limit, List<String> type) {
        if(limit > 100) limit = 100;
        String where = createWhereString(match);
        return "SELECT " + COLUMNS + " FROM " + TABLE + " " + where + " " + statementType(type) + " ORDER BY RAND()" + " LIMIT " + limit + ";";
    }

    static String where(String match, int limit, List<String> whereList){
        if(limit > 100) limit = 100;
        String where = createWhereString(match);
        return "SELECT " + COLUMNS + " FROM " + TABLE + " " + where + " " + statementWhere(whereList) + " ORDER BY RAND()" + " LIMIT " + limit + ";";
    }

    static String whereAndType(String match, int limit, List<String> whereList, List<String> type) {
        if(limit > 100) limit = 100;
        String where = createWhereString(match);
        return "SELECT " + COLUMNS + " FROM " + TABLE + " " + where + " " + statementWhere(whereList) + " " + statementType(type) + " ORDER BY RAND()" + " LIMIT " + limit + ";";
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
        return "SELECT " + data + " FROM " + TABLE + " " + where + " " + " ORDER BY RAND() " + limit + ";";
    }

    static String statementWhere(List<String> where) {
        String whereString = " AND (";
        for (int i = 0; i < where.size(); i++) {
            String port = where.get(i);
            if (i == where.size() - 1) {
                whereString += "(world.municipality LIKE \"%" + port + "%\" OR region.name LIKE \"%" + port + "%\" OR country.name LIKE \"%" + port + "%\")";
            } else {
                whereString += "(world.municipality LIKE \"%" + port + "%\" OR region.name LIKE \"%" + port + "%\" OR country.name LIKE \"%" + port + "%\") OR ";
            }
        }
        whereString += ")";
        return whereString;
    }


    static String statementType(List<String> type) {
        return TypeFilter.generateTypeString(type);
    }

    static String statementNear(String where, String data, String limit, Place place) {
        return "SELECT " + data + " FROM " + TABLE + " " + where + " ORDER BY ABS(latitude - " + place.get("latitude") + ") + ABS(longitude - " + place.get("longitude") + ") " + limit + ";";
    }
}