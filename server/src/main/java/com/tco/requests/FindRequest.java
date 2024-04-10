package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tco.misc.OptimizerFactory;
import com.tco.misc.TourOptimizer;
import com.tco.misc.OneOpt;
import com.tco.query.NearLocations;
import com.tco.misc.BadRequestException;
import com.tco.query.FindLocations;

import java.util.ArrayList;
import java.util.List;


public class FindRequest extends Request{

    private Places places = new Places();
    String match;
    List<String> type;
    List<String> where;
    int limit;


    private final static transient Logger log = LoggerFactory.getLogger(FindRequest.class);

    public FindRequest(String match, List<String> type, List<String> where, Integer limit) {
        this.match = match;
        this.type = type;
        this.where = where;
        this.limit = limit;
    }
    
    @Override
    public void buildResponse() throws BadRequestException{
        try {
            FindLocations locations = new FindLocations(this.match, this.type, this.where, this.limit);
            this.places = locations.find();
            this.limit = locations.found();
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
