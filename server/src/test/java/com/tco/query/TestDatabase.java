package com.tco.query;

import com.tco.requests.Place;
import com.tco.requests.Places;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDatabase {

    static class ResultSetConverter {
        
        public static String resultSetToString(ResultSet rs) throws SQLException {
            StringBuilder sb = new StringBuilder();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Append column names (header)
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) sb.append(",  ");
                sb.append(rsmd.getColumnName(i));
            }
            sb.append("\n");

            // Append rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1) sb.append(",  ");
                    sb.append(rs.getString(i));
                }
                sb.append("\n");
            }

            return sb.toString();
        }
    }

    // Integration Testing

    @Test
    @DisplayName("bscheidt: test valid performQuery")
    public void testValidPerformQuery() {
        try {
            String sql = "show tables;";
            String expected = "TABLE_NAME\ncontinent\ncountry\nregion\nworld\n";

            ResultSet resultSet = Database.performQuery(sql);

            String actual = ResultSetConverter.resultSetToString(resultSet);

            assertEquals(expected, actual);
        }
        catch(Exception e) {
            fail("Shouldnt have thrown an exception when querying the DB.");
        }
        
    }

    @Test
    @DisplayName("bscheidt: test invalid performQuery")
    public void testInvalidPerformQuery() {
        String sql = "invalid query";
        assertThrows(Exception.class, () -> {
            Database.performQuery(sql);
        });
    }

    @Test
    @DisplayName("bscheidt: test complex performQuery")
    public void testComplexPerformQuery() {
        try {
            String sql = "select id,name,municipality,type from world where id in ('19CO','26CO','77CO','CO23','CO24','K00V','KFNL','KDEN');";

            String expected = "id,  name,  municipality,  type\n" +
                            "19CO,  Memorial Hospital Heliport,  Colorado Springs,  heliport\n" +
                            "26CO,  Lockheed Martin Cmd & Cntrl Sys Heliport,  Colorado Springs,  heliport\n" +
                            "77CO,  Memorial North Helipad,  Colorado Springs,  heliport\n" +
                            "CO23,  St Francis Hospital Heliport,  Colorado Springs,  heliport\n" +
                            "CO24,  Penrose Hospital Heliport,  Colorado Springs,  heliport\n" +
                            "K00V,  Meadow Lake Airport,  Colorado Springs,  small_airport\n" +
                            "KDEN,  Denver International Airport,  Denver,  large_airport\n" +
                            "KFNL,  Fort Collins Loveland Municipal Airport,  Fort Collins/Loveland,  small_airport\n";

            ResultSet resultSet = Database.performQuery(sql);

            String actual = ResultSetConverter.resultSetToString(resultSet);

            assertEquals(expected, actual);
        }
        catch(Exception e) {
            fail("Shouldnt have thrown an exception when querying the DB.");
        }
        
    }

    @Test
    @DisplayName("bscheidt: test valid found query")
    public void testValidFound() {
        try {
            String sql = "select COUNT(*) AS count from continent;";

            int expected = 7;
            
            int actual = Database.found(sql);

            assertEquals(expected, actual);
        }
        catch(Exception e) {
            fail("Shouldnt have thrown an exception when querying the DB.");
        }
        
    }

    @Test
    @DisplayName("bscheidt: test invalid found query")
    public void testInalidFound() {
        String sql = "invalid query";

        assertThrows(Exception.class, () -> {
            Database.found(sql);
        });
        
    }

    @Test
    @DisplayName("lewi0027:testing Places() method")
    public void testPlacesMethod() {
        try{    
            Places actual = Database.places("SELECT name,municipality,latitude,longitude,altitude,type FROM world WHERE latitude BETWEEN 5.0 AND 25.0 AND longitude BETWEEN 30.0 AND 40.0 ORDER BY ABS(latitude - 15) + ABS(longitude - 35) LIMIT 130;");

            assertEquals(105, actual.size());
        } catch(Exception e) {
            StringBuilder errorMessage = new StringBuilder("Shouldn't have thrown an exception when querying the DB. ");
        }        
    }

    @Test
    @DisplayName("lewi0027:testing Places() method with an Empty Set return")
    public void testPlacesMethodEmptySet() {
        try{    
            Places actual = Database.places("SELECT name,municipality,latitude,longitude,altitude,type FROM world WHERE latitude BETWEEN 75.0 AND 85.0 AND longitude BETWEEN 30.0 AND 40.0 ORDER BY ABS(latitude - 15) + ABS(longitude - 35) LIMIT 130;");

            assertEquals(0, actual.size());
        } catch(Exception e) {
            StringBuilder errorMessage = new StringBuilder("Shouldn't have thrown an exception when querying the DB. ");
        }        
    }
    
}
