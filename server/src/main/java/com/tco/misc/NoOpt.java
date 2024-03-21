package com.tco.misc;

import com.tco.requests.Places;


public class NoOpt extends TourOptimizer {
    public Places construct(Places places, Double earthRadius, String formula, Double response){
        return places;
    }

    public void improve(){
        return;
    }
}
