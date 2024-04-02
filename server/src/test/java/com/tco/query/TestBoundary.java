package com.tco.query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoundary {

    private Boundary boundary;

    @Test
    @DisplayName("bscheidt: test default constructor")
    public void testDefaultConstructor() {
        boundary = new Boundary();
        String expected = "latMin: 0.0\nlatMax: 0.0\nlonMin: 0.0\nlonMax: 0.0\n";
        assertEquals(expected, boundary.toString());
    }

    @Test
    @DisplayName("bscheidt: test overloaded constructor")
    public void testOverloadedConstructor() {
        boundary = new Boundary(1, 2, 3, 4);
        String expected = "latMin: 1.0\nlatMax: 2.0\nlonMin: 3.0\nlonMax: 4.0\n";
        assertEquals(expected, boundary.toString());
    }

    @Test
    @DisplayName("bscheidt: test copy constructor")
    public void testCopyConstructor() {
        Boundary toCopy = new Boundary(1, 2, 3, 4);
        boundary = new Boundary(toCopy);
        String expected = "latMin: 1.0\nlatMax: 2.0\nlonMin: 3.0\nlonMax: 4.0\n";
        assertEquals(expected, boundary.toString());
    }   

    @Test
    @DisplayName("bscheidt: test modifying structure")
    public void testModifyStructure() {
        boundary = new Boundary(1, 2, 3, 4);
        boundary.latMin = 5;
        boundary.latMax = 6;
        boundary.lonMin = 7;
        boundary.lonMax = 8;
        String expected = "latMin: 5.0\nlatMax: 6.0\nlonMin: 7.0\nlonMax: 8.0\n";
        assertEquals(expected, boundary.toString());
    }   
}
