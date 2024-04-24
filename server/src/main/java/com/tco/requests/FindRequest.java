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

    private final String match;
    private final List<String> type;
    private final List<String> where;
    private final Integer limit;
    private Integer found;
    private Places places;


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
            determineWhereType(locations);
        }
        catch (Exception e) {
            log.error("Error processing request: ", e);
            throw new BadRequestException();
        }
        finally {
            log.trace("buildResponse -> {}", this);
        }
    }

    public void determineWhereType(FindLocations locations) throws BadRequestException{
        try{
            if ((this.where != null && !this.where.isEmpty()) && (this.type != null && !this.type.isEmpty())) {
                this.places = locations.whereAndType();
                this.found = locations.foundWhereAndType();
            } 
            else if(this.where != null && !this.where.isEmpty()){
                this.places = locations.where();
                this.found = locations.foundWhere();
            }
            else if(this.type != null && !this.type.isEmpty()){
                this.places = locations.type();
                this.found = locations.foundType();
            }
            else {
                this.places = locations.find();
                this.found = locations.found();
            }
        }  catch (Exception e) {
            log.error("Error processing request: ", e);
            throw new BadRequestException();
        }
    }
}