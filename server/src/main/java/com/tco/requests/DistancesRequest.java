package com.tco.requests;

import com.tco.misc.GeographicCoordinate;
import com.tco.misc.BadRequestException;
import com.tco.misc.GreatCircleDistance;
import com.tco.misc.FormulaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistancesRequest extends Request {
    private final Places places;
    private final Double earthRadius;
    private final String formula;
    private Distances distances;

    private final static transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);

    public DistancesRequest(Places places, Double earthRadius, String formula) {
        this.places = places;
        this.earthRadius = earthRadius;
        this.formula = formula;
    }

    @Override
    public void buildResponse() throws BadRequestException {
        try {
            validateRequest();
            distances = buildDistanceList();
        }
        catch (BadRequestException e) {
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
        return !formula.equalsIgnoreCase("Vincenty") && !formula.equalsIgnoreCase("Haversine") && !formula.equalsIgnoreCase("Cosines")
    }

    private Distances buildDistanceList(){
        Distances distances = new Distances();
        
        // Add ability to calculate new distances
        GreatCircleDistance distanceCalculator = FormulaFactory.createFormula(formula);

        for(int i = 0; i < places.size(); i++){
            GeographicCoordinate from = places.get(i);
            GeographicCoordinate to = places.get((i + 1) % places.size());

            long distance = distanceCalculator.between(from, to, earthRadius);
            distances.add(distance);
        }
        
        return distances;
    }

    
    // for testing
    public Distances getDistances() {
        return this.distances;
    }

    public Double getEarthRadius() {
        return this.earthRadius;
    }

    public String getFormula() {
        return this.formula;
    }

    public Places getPlaces() {
        return this.places;
    }
}
