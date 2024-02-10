package com.tco.requests;

import com.tco.misc.GeographicCoordinate;
import com.tco.misc.BadRequestException;

public class DistancesRequest extends Request {
    private final Places places;
    private Distances distances;
    private final Double earthRadius;
    private final String formula;

    public DistancesRequest(Places places, Double earthRadius, String formula) {
        this.places = places;
        this.earthRadius = earthRadius;
        this.formula = formula;
        this.distances = new Distances();
    }

    @Override
    public void buildResponse() throws BadRequestException {
        
    }
}
