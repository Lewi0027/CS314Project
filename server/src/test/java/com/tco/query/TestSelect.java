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
    final String keyword = "keyword";
    Place place;    
    Boundary boundOne;

    public TestSelect() {
        place = new Place("15", "35");
        boundOne = new Boundary(5, 25, 30, 40);
    }

    @Test
    @DisplayName("Lewi0027: Test statementNear for correct output")
    public void testStatementNear() {
        assertEquals( "SELECT data FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id where ORDER BY ABS(latitude - 15) + ABS(longitude - 35) LIMIT 5;", Select.statementNear(where, data, limit, place) );
    }

    @Test
    @DisplayName("Wyattg5: Test near for correct output")
    public void testNear() {
        assertEquals( "SELECT  world.id AS id, world.name, world.municipality, region.name AS region, country.name AS country, world.latitude, world.longitude, world.altitude, world.type FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE latitude BETWEEN 5.0 AND 25.0 AND longitude BETWEEN 30.0 AND 40.0 ORDER BY ABS(latitude - 15) + ABS(longitude - 35) LIMIT 200;", Select.near(boundOne, place) );
    }

    //Tests for found
    @Test
    @DisplayName("Diegocel: Tests found for correct output")
    public void testFoundCorrect(){
        String match = "Barcelona";
        String expectedQuery = "SELECT COUNT(*) AS count FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE world.name LIKE \"%Barcelona%\" OR world.id LIKE \"%Barcelona%\" OR world.municipality LIKE \"%Barcelona%\" OR region.name LIKE \"%Barcelona%\" OR country.name LIKE \"%Barcelona%\" ;";
        String actualQuery = Select.found(match);
        assertEquals(expectedQuery, actualQuery, "Query should match");
    }

    @Test
    @DisplayName("Diegocel: Test for found with no match")
    public void testFoundNoMatch(){
        String match = "";
        String expectedQuery = "SELECT COUNT(*) AS count FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE world.name LIKE \"%%\" OR world.id LIKE \"%%\" OR world.municipality LIKE \"%%\" OR region.name LIKE \"%%\" OR country.name LIKE \"%%\" ;";
        String actualQuery = Select.found(match);
        assertEquals(expectedQuery, actualQuery, "Query should match");
    }

    @Test
    @DisplayName("Wyattg5: Test statementFind for correct output")
    public void testStatementFind() {
        assertEquals( "SELECT data FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id where LIMIT 5;", Select.statementFind(where, data, limit) );
    }

    @Test
    @DisplayName("Lewi0027: Test match() method for return value")
    public void testMatchMethod() {
        assertEquals( "SELECT  world.id AS id, world.name, world.municipality, region.name AS region, country.name AS country, world.latitude, world.longitude, world.altitude, world.type  FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE world.name LIKE \"%keyword%\" OR world.id LIKE \"%keyword%\" OR world.municipality LIKE \"%keyword%\" OR region.name LIKE \"%keyword%\" OR country.name LIKE \"%keyword%\" LIMIT 5;", Select.match(keyword, 5) );
    }
}
