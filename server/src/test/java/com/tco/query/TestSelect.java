package com.tco.query;

import com.tco.requests.Place;
import com.tco.query.Boundary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.ArrayList;

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
        String expectedQuery = "SELECT COUNT(*) AS count FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE (world.name LIKE \"%Barcelona%\" OR world.id LIKE \"%Barcelona%\" OR world.municipality LIKE \"%Barcelona%\" OR region.name LIKE \"%Barcelona%\" OR country.name LIKE \"%Barcelona%\")  ORDER BY RAND() ;";
        String actualQuery = Select.found(match);
        assertEquals(expectedQuery, actualQuery, "Query should match");
    }

    @Test
    @DisplayName("Diegocel: Test for found with no match")
    public void testFoundNoMatch(){
        String match = "";
        String expectedQuery = "SELECT COUNT(*) AS count FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE (world.name LIKE \"%%\" OR world.id LIKE \"%%\" OR world.municipality LIKE \"%%\" OR region.name LIKE \"%%\" OR country.name LIKE \"%%\")  ORDER BY RAND() ;";
        String actualQuery = Select.found(match);
        assertEquals(expectedQuery, actualQuery, "Query should match");
    }

    @Test
    @DisplayName("Wyattg5: Test statementFind for correct output")
    public void testStatementFind() {
        assertEquals( "SELECT data FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id where  ORDER BY RAND() LIMIT 5;", Select.statementFind(where, data, limit) );
    }

    @Test
    @DisplayName("Lewi0027: Test match() method for return value")
    public void testMatchMethod() {
        assertEquals( "SELECT  world.id AS id, world.name, world.municipality, region.name AS region, country.name AS country, world.latitude, world.longitude, world.altitude, world.type  FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE (world.name LIKE \"%keyword%\" OR world.id LIKE \"%keyword%\" OR world.municipality LIKE \"%keyword%\" OR region.name LIKE \"%keyword%\" OR country.name LIKE \"%keyword%\")  ORDER BY RAND() LIMIT 5;", Select.match(keyword, 5) );
    }

    @Test
    @DisplayName("bscheidt: test generateTypeString() all fields")
    public void testFullType() {
        String expected = " AND (world.type LIKE \"%airport%\" OR world.type LIKE \"%heliport%\" OR world.type LIKE \"%balloonport%\") ";

        List<String> type = new ArrayList<>();
        type.add("airport");
        type.add("heliport");
        type.add("balloonport");

        String actual = TypeFilter.generateTypeString(type);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("bscheidt: test generateTypeString() one field")
    public void testOneType() {
        String expected = " AND (world.type LIKE \"%airport%\") ";

        List<String> type = new ArrayList<>();
        type.add("airport");

        String actual = TypeFilter.generateTypeString(type);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("bscheidt: test statementType() all fields")
    public void testStatementTypeAllFields() {
        List<String> type = new ArrayList<>();
        type.add("airport");
        type.add("heliport");
        type.add("balloonport");

        String expected = " AND (world.type LIKE \"%airport%\" OR world.type LIKE \"%heliport%\" OR world.type LIKE \"%balloonport%\") ";
        

        String actual = Select.statementType(type);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("bscheidt: test statementType() other field")
    public void testStatementTypeOtherField() {
        List<String> type = new ArrayList<>();
        type.add("other");

        String expected = " AND ((world.type NOT LIKE \"%airport%\" AND world.type NOT LIKE \"%heliport%\" AND world.type NOT LIKE \"%balloonport%\")) ";
        

        String actual = Select.statementType(type);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("bscheidt: test type() all fields")
    public void testTypeAllFields() {
        List<String> type = new ArrayList<>();
        type.add("airport");
        type.add("heliport");
        type.add("balloonport");

        String expected = "SELECT  world.id AS id, world.name, world.municipality, region.name AS region, country.name AS country, world.latitude, world.longitude, world.altitude, world.type FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE (world.name LIKE \"%keyword%\" OR world.id LIKE \"%keyword%\" OR world.municipality LIKE \"%keyword%\" OR region.name LIKE \"%keyword%\" OR country.name LIKE \"%keyword%\")";
        expected += "  AND (world.type LIKE \"%airport%\" OR world.type LIKE \"%heliport%\" OR world.type LIKE \"%balloonport%\")  ORDER BY RAND() LIMIT 5;";
        

        String actual = Select.type("keyword", 5, type);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("bscheidt: test type() other field")
    public void testTypeOtherField() {
        List<String> type = new ArrayList<>();
        type.add("other");

        String expected = "SELECT  world.id AS id, world.name, world.municipality, region.name AS region, country.name AS country, world.latitude, world.longitude, world.altitude, world.type FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE (world.name LIKE \"%try%\" OR world.id LIKE \"%try%\" OR world.municipality LIKE \"%try%\" OR region.name LIKE \"%try%\" OR country.name LIKE \"%try%\")";
        expected += "  AND ((world.type NOT LIKE \"%airport%\" AND world.type NOT LIKE \"%heliport%\" AND world.type NOT LIKE \"%balloonport%\"))  ORDER BY RAND() LIMIT 5;";
        

        String actual = Select.type("try", 5, type);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("bscheidt: test type() other and airport") 
    public void testTypeOtherAndAirport() {
        List<String> type = new ArrayList<>();
        type.add("airport");
        type.add("other");

        String expected = "SELECT  world.id AS id, world.name, world.municipality, region.name AS region, country.name AS country, world.latitude, world.longitude, world.altitude, world.type  FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE (world.name LIKE \"%try%\" OR world.id LIKE \"%try%\" OR world.municipality LIKE \"%try%\" OR region.name LIKE \"%try%\" OR country.name LIKE \"%try%\")";
        expected += "  AND (world.type LIKE \"%airport%\" OR (world.type NOT LIKE \"%heliport%\" AND world.type NOT LIKE \"%balloonport%\")) LIMIT 5;";
        

        String actual = Select.type("try", 5, type);
    }

    @Test
    @DisplayName("diegocel: test where one city")
    public void testWhereOneCity(){
        List<String> where = new ArrayList<>();
        where.add("Denver");
        String match = "match";
        int limit = 5;
        String expectedQuery = "SELECT  world.id AS id, world.name, world.municipality, region.name AS region, country.name AS country, world.latitude, world.longitude, world.altitude, world.type FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE (world.name LIKE \"%match%\" OR world.id LIKE \"%match%\" OR world.municipality LIKE \"%match%\" OR region.name LIKE \"%match%\" OR country.name LIKE \"%match%\")  AND ((world.municipality LIKE \"%Denver%\" OR region.name LIKE \"%Denver%\" OR country.name LIKE \"%Denver%\")) ORDER BY RAND() LIMIT 5;"; 
        String actualQuery = Select.where(match, limit, where);
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("diegocel: foundWhere test")
    public void testFoundWhere(){
        List<String> where = new ArrayList<>();
        where.add("Denver");
        String match = "match";

        String expectedQuery = "SELECT COUNT(*) AS count FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE (world.name LIKE \"%match%\" OR world.id LIKE \"%match%\" OR world.municipality LIKE \"%match%\" OR region.name LIKE \"%match%\" OR country.name LIKE \"%match%\")  AND ((world.municipality LIKE \"%Denver%\" OR region.name LIKE \"%Denver%\" OR country.name LIKE \"%Denver%\"));"; 
        String actualQuery = Select.foundWhere(match, where);
        assertEquals(expectedQuery, actualQuery);
    }


    @Test
    @DisplayName("lewi0027: statementWhere test")
    public void testStatementWhereString() {
        List<String> where = new ArrayList<>();
        where.add("Canada");

        String expected = " AND ((world.municipality LIKE \"%Canada%\" OR region.name LIKE \"%Canada%\" OR country.name LIKE \"%Canada%\"))";
        

        String actual = Select.statementWhere(where);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("bscheidt: test type() all fields and other") 
    public void testTypeAllFieldsAndOther() {
        List<String> type = new ArrayList<>();
        type.add("airport");
        type.add("heliport");
        type.add("balloonport");
        type.add("other");

        String expected = "SELECT  world.id AS id, world.name, world.municipality, region.name AS region, country.name AS country, world.latitude, world.longitude, world.altitude, world.type FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE (world.name LIKE \"%try%\" OR world.id LIKE \"%try%\" OR world.municipality LIKE \"%try%\" OR region.name LIKE \"%try%\" OR country.name LIKE \"%try%\")  ORDER BY RAND() LIMIT 5;";   

        String actual = Select.type("try", 5, type);

        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("lewi0027: statementWhere test")
    public void testStatementWhere() {

        List<String> where2 = new ArrayList<>();
        where2.add("Canada");

        String expected = " AND ((world.municipality LIKE \"%Canada%\" OR region.name LIKE \"%Canada%\" OR country.name LIKE \"%Canada%\"))";
        
        String actual = Select.statementWhere(where2);

        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("bscheidt: whereAndType test")
    public void testWhereAndType() {

        List<String> whereList = new ArrayList<>();
        whereList.add("Canada");

        List<String> typeList = new ArrayList<>();
        typeList.add("heliport");

        String select = "SELECT  world.id AS id, world.name, world.municipality, region.name AS region, country.name AS country, world.latitude, world.longitude, world.altitude, world.type FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id ";

        String match = "WHERE (world.name LIKE \"%den%\" OR world.id LIKE \"%den%\" OR world.municipality LIKE \"%den%\" OR region.name LIKE \"%den%\" OR country.name LIKE \"%den%\")  ";

        String where = "AND ((world.municipality LIKE \"%Canada%\" OR region.name LIKE \"%Canada%\" OR country.name LIKE \"%Canada%\"))  ";

        String type = "AND (world.type LIKE \"%heliport%\")  ORDER BY RAND() LIMIT 10;";

        String expected = select + match + where + type;
        
        String actual = Select.whereAndType("den", 10, whereList, typeList);

        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("bscheidt: whereAndType test complex")
    public void testWhereAndTypeComplex() {

        List<String> whereList = new ArrayList<>();
        whereList.add("Canada");
        whereList.add("Alaska");

        List<String> typeList = new ArrayList<>();
        typeList.add("heliport");
        typeList.add("other");

        String select = "SELECT  world.id AS id, world.name, world.municipality, region.name AS region, country.name AS country, world.latitude, world.longitude, world.altitude, world.type FROM world INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id ";

        String match = "WHERE (world.name LIKE \"%den%\" OR world.id LIKE \"%den%\" OR world.municipality LIKE \"%den%\" OR region.name LIKE \"%den%\" OR country.name LIKE \"%den%\")  ";

        String where = "AND ((world.municipality LIKE \"%Canada%\" OR region.name LIKE \"%Canada%\" OR country.name LIKE \"%Canada%\") OR (world.municipality LIKE \"%Alaska%\" OR region.name LIKE \"%Alaska%\" OR country.name LIKE \"%Alaska%\"))  ";

        String type = "AND (world.type LIKE \"%heliport%\" OR (world.type NOT LIKE \"%airport%\" AND world.type NOT LIKE \"%balloonport%\"))  ORDER BY RAND() LIMIT 10;";

        String expected = select + match + where + type;
        
        String actual = Select.whereAndType("den", 10, whereList, typeList);

        assertEquals(expected, actual);
    }
}
