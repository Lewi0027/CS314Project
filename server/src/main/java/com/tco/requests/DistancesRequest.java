package com.tco.requests;

import com.tco.misc.GeographicCoordinate;
import com.tco.misc.BadRequestException;

public class DistancesRequest extends Request {
    private Places places;
    private Distances distances;
    private Double earthRadius;
    private String formula;

    @Override
    public void buildResponse() throws BadRequestException {
        
    }
}
