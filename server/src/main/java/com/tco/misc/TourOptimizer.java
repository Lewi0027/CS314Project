package com.tco.misc;

import com.tco.requests.Places;

public abstract class TourOptimizer {

    private long[][] distanceMatrix;
    private int[] tour;

    public Places construct(Places places, Double earthRadius, String formula, Double response){
        return places;
    }

    public abstract void improve(); 

}
