package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tco.misc.OptimizerFactory;
import com.tco.misc.TourOptimizer;
import com.tco.misc.OneOpt;
import com.tco.query.NearLocations;
import com.tco.misc.BadRequestException;

import java.util.ArrayList;


public class NearRequest extends Request{
    Places places;
    Place place;
    Double earthRadius;
    Integer distance;
    Integer limit;
    Distances distances;
    String formula;

    private final static transient Logger log = LoggerFactory.getLogger(NearRequest.class);

    public NearRequest(Place place, int distance, double earthRadius, int limit, String formula){
        this.place = place;
        this.distance = distance;
        this.earthRadius = earthRadius;
        this.limit = limit;
        this.formula = formula;
    }

    @Override
    public void buildResponse() throws BadRequestException{
        try {
            validateRequest();
            NearLocations locations = new NearLocations(this.place, this.distance, this.earthRadius, this.limit, this.formula);
            this.places = locations.near();
            this.distances = locations.distances();
        } 
        catch (Exception e) {
            log.error("Error processing request: ", e);
            throw new BadRequestException();
        }
        finally {
            log.trace("buildResponse -> {}", this);
        }
    }

    private void validateRequest() throws BadRequestException {
        if(formula != null && isInvalidFormula()) 
            throw new BadRequestException();
    }

    private boolean isInvalidFormula() {
        return !formula.equalsIgnoreCase("Vincenty") && !formula.equalsIgnoreCase("Haversine") && !formula.equalsIgnoreCase("Cosines");
    }
}
