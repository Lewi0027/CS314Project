package com.tco.requests;

import com.tco.misc.GeographicCoordinate;
import com.tco.misc.BadRequestException;

public class DistancesRequest extends Request {
    private final Places places;
    private Distances distances;
    private final Double earthRadius;
    private final String formula;

    @Override
    public void buildResponse() throws BadRequestException {
        
    }
}
