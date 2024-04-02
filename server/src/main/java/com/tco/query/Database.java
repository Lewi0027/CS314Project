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
        private final static String COLUMNS = "id,name,municipality,iso_region,iso_country,latitude,longitude,altitude,type";

        private static ResultSet performQuery (String sql) throws Exception {
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
            return 0;
        }

        static Places places(String sql) throws Exception {
            return null;
        }

    }



