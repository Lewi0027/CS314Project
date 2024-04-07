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

    private final static transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);

    public NearRequest(){
        this.place = place;
        this.distance = distance;
        this.earthRadius = earthRadius;
        this.limit = limit;
        this.formula = formula;
    }

    @Override
    public void buildResponse() throws BadRequestException{
        try {
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
}
