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
        //constructor
    }

    @Override
    public void buildResponse() throws BadRequestException{

    }
}
