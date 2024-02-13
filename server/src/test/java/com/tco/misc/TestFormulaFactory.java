package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFormulaFactory {
    
    FormulaFactory formulaFactory;

    @BeforeEach
    public void setupFactory() {
        formulaFactory = new FormulaFactory();
    }

    @Test
    @DisplayName("ajlei: null should return Vincenty formula")
    public void testNull() {
        assertEquals("com.tco.misc.Vincenty", formulaFactory.createFormula(null).getClass().getName());
    }

    @Test
    @DisplayName("ajlei: VINCENTY should return Vincenty formula")
    public void testVincentyUpper() {
        assertEquals("com.tco.misc.Vincenty", formulaFactory.createFormula("VINCENTY").getClass().getName());
    }

    @Test
    @DisplayName("ajlei: vincenty should return Vincenty formula")
    public void testVincentyLower() {
        assertEquals("com.tco.misc.Vincenty", formulaFactory.createFormula("vincenty").getClass().getName());
    }

    @Test
    @DisplayName("ajlei: HAVERSINE should return Vincenty formula")
    public void testHaversineUpper() {
        assertEquals("com.tco.misc.Haversine", formulaFactory.createFormula("HAVERSINE").getClass().getName());
    }

    @Test
    @DisplayName("ajlei: vincenty should return Vincenty formula")
    public void testHaversineLower() {
        assertEquals("com.tco.misc.Haversine", formulaFactory.createFormula("haversine").getClass().getName());
    }

    @Test
    @DisplayName("ajlei: COSINES should return Vincenty formula")
    public void testCosinesUpper() {
        assertEquals("com.tco.misc.Cosines", formulaFactory.createFormula("COSINES").getClass().getName());
    }

    @Test
    @DisplayName("ajlei: cosines should return Vincenty formula")
    public void testCosinesLower() {
        assertEquals("com.tco.misc.Cosines", formulaFactory.createFormula("cosines").getClass().getName());
    }

    @Test
    @DisplayName("ajlei: Differing string should return Vincenty formula")
    public void testDiffering() {
        assertEquals("com.tco.misc.Vincenty", formulaFactory.createFormula("TestingString").getClass().getName());
    }
}
