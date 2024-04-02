package com.tco.query;

import com.tco.requests.Place;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSelect {
    
    final String where = "where";
    final String data = "data"; 
    final String limit = "LIMIT 5";
    Place place;

    public TestSelect() {
        place = new Place("15", "35");
    }

    @Test
    @DisplayName("Lewi0027: Test statementNear for correct output")
    public void testStatementNear() {
        assertEquals( "SELECT data FROM world where ORDER BY ABS(latitude - 15) + ABS(longitude - 35) LIMIT 5;", Select.statementNear(where, data, limit, place) );
    }

}
