package com.tco.query;

import com.tco.requests.Place;
import com.tco.query.Boundary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSelect {
    
    final String where = "where";
    final String data = "data"; 
    final String limit = "LIMIT 5";
    Place place;    
    Boundary boundOne;

    public TestSelect() {
        place = new Place("15", "35");
        boundOne = new Boundary(5, 25, 30, 40);
    }

    @Test
    @DisplayName("Lewi0027: Test statementNear for correct output")
    public void testStatementNear() {
        assertEquals( "SELECT data FROM world where ORDER BY ABS(latitude - 15) + ABS(longitude - 35) LIMIT 5;", Select.statementNear(where, data, limit, place) );
    }

    @Test
    @DisplayName("wyattg5: Test near for correct output")
    public void testNear() {
        assertEquals( "SELECT id,name,municipality,iso_region,iso_country,latitude,longitude,altitude,type FROM world " +
        "WHERE latitude BETWEEN 5.0 AND 25.0 AND longitude BETWEEN 30.0 AND 40.0 ORDER BY ABS(latitude - 15) + ABS(longitude - 35) " + 
        "LIMIT 200;", Select.near(boundOne, place) );
    }

    //Tests for found
    @Test
    @DisplayName("Diegocel: Tests found for correct output")
    public void testFoundCorrect(){
        String match = "Barcelona";
        String expectedQuery = "SELECT COUNT(*) AS count FROM world WHERE name LIKE \"%Barcelona%\";";
        String actualQuery = Select.found(match);
        assertEquals(expectedQuery, actualQuery, "Query should match");
    }

    @Test
    @DisplayName("Diegocel: Test for found with no match")
    public void testFoundNoMatch(){
        String match = "";
        String expectedQuery = "SELECT COUNT(*) AS count FROM world WHERE name LIKE \"%%\";";
        String actualQuery = Select.found(match);
        assertEquals(expectedQuery, actualQuery, "Query should match");
    }
}
