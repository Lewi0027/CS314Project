package com.tco.requests;

import com.tco.misc.BadRequestException;

public class FormulaValidator{
    
    public void validateRequest(String formula) throws BadRequestException {
        if(formula != null && isInvalidFormula(formula)) 
            throw new BadRequestException();
    }

    public boolean isInvalidFormula(String formula) {
        return !formula.equalsIgnoreCase("Vincenty") && !formula.equalsIgnoreCase("Haversine") && !formula.equalsIgnoreCase("Cosines");
    }
}
