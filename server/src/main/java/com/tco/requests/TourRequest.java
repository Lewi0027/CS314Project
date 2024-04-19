package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tco.misc.OptimizerFactory;
import com.tco.misc.TourOptimizer;
import com.tco.misc.BadRequestException;

import java.util.ArrayList;

public class TourRequest extends Request{
    
    private Places places;
    private final Double earthRadius;
    private final String formula;
    private final Double response;

    private final static transient Logger log = LoggerFactory.getLogger(TourRequest.class);

    public TourRequest(Places places, Double earthRadius, String formula, Double response) {
        this.places = places;
        this.earthRadius = earthRadius;
        this.formula = formula;
        this.response = response;
    }

    @Override
    public void buildResponse() throws BadRequestException {
        try {
            FormulaValidator formulaValidator = new FormulaValidator();
            formulaValidator.validateRequest(this.formula);
            if(this.response == 0.0) return;
            constructPlacesWithOptimizer();
        }
        catch (BadRequestException e) {
            log.error("Error processing request: ", e);
            throw new BadRequestException();
        }
        finally {
            log.trace("buildResponse -> {}", this);
        }
    }

    protected void constructPlacesWithOptimizer() {
        TourOptimizer optimizer = OptimizerFactory.createOptimizer(this.places.size(), this.response);
        this.places = optimizer.construct(this.places, this.earthRadius, this.formula, this.response);
    }

    // for testing
    protected Places getPlaces() {
        return this.places;
    }
}
