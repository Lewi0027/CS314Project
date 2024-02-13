package com.tco.misc;

public class FormulaFactory {

    public static GreatCircleDistance createFormula(String formula) {
        if(formula == null || formula.equalsIgnoreCase("Vincenty")) 
            return new Vincenty();

        if(formula.equalsIgnoreCase("Haversine"))
            return new Haversine();

        if(formula.equalsIgnoreCase("Cosines"))
            return new Cosines();
        
        return new Vincenty();
    }
    
}