package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tco.misc.OptimizerFactory;
import com.tco.misc.TourOptimizer;
import com.tco.misc.OneOpt;
import com.tco.query.NearLocations;
import com.tco.misc.BadRequestException;

import java.util.ArrayList;


public class FindRequest extends Request{

    private final static transient Logger log = LoggerFactory.getLogger(FindRequest.class);
    
    @Override
    public void buildResponse() throws BadRequestException{
        try {
            
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
