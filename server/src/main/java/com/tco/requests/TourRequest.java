package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class TourRequest extends Request{
    
    private Places places;
    private final Double earthRadius;
    private final String formula;
    private final Integer response;

    private final static transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);

    public TourRequest(Places places, Double earthRadius, String formula, Integer response) {
        this.places = places;
        this.earthRadius = earthRadius;
        this.formula = formula;
        this.response = response;
    }

    @Override
    public void buildResponse() {
        log.trace("buildResponse -> {}", this);
    }
}
