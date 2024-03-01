package com.tco.misc;

import com.tco.requests.Places;

public abstract class TourOptimizer {

    private long[][] distanceMatrix;
    private int[] tour;

    public Places construct(Places places, Double earthRadius, String formula, Double response){
        fillDistanceMatrix(earthRadius, formula, places);
        return places;
    }

    protected void fillDistanceMatrix(Double earthRadius, String formula, Places places) {
        int tourLength = places.size();
        this.distanceMatrix = new long[tourLength][tourLength];
        GreatCircleDistance calculator = FormulaFactory.createFormula(formula);

        for(int from = 0; from < tourLength; from++) {
            GeographicCoordinate fromPlace = places.get(from);
            for(int to = 0; to < tourLength; to++) {
                GeographicCoordinate toPlace = places.get(to);
                long distance = calculator.between(fromPlace, toPlace, earthRadius);
                this.distanceMatrix[from][to] = distance;
            }
        }
    }

    public abstract void improve(); 


    // for testing
    protected long[][] getDistanceMatrix() {
        return this.distanceMatrix;
    }

}
