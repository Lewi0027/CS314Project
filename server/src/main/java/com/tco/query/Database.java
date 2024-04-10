package com.tco.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.tco.requests.Place;
import com.tco.requests.Places;

public class Database {

    private final static String TABLE = "world";
    private final static String COLUMN = "name";
    private final static String COLUMNS = "id,name,municipality,region,country,latitude,longitude,altitude,type";

    protected static ResultSet performQuery (String sql) throws Exception {
        try (
                // connect to the database and query
                Connection conn = DriverManager.getConnection(Credential.getUrl(), Credential.USER,
                    Credential.PASSWORD);
                Statement query = conn.createStatement();
        ) {
            ResultSet results = query.executeQuery(sql);
            return results;
        } catch (Exception e) {
            throw e;
        }

    }

    static Integer found(String sql) throws Exception {
        ResultSet results = performQuery(sql);
        if (!results.next()) {
            throw new Exception("No count results in found query.");
        }
        return results.getInt("count");
    }

    static Places places(String sql) throws Exception {
        ResultSet results = performQuery(sql);
        String columns = COLUMNS;
        int count = 0;
        String[] cols = columns.split(",");
        Places places = new Places();
        while (results.next()) {
            Place place = new Place();
            for (String col : cols) {
                place.put(col, results.getString(col));
            }
            places.add(place);
        }
        return places;
    }

}